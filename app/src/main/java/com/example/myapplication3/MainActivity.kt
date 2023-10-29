package com.example.myapplication3

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication3.sample.MainViewModel
import com.example.myapplication3.sample.model.Post
import com.example.myapplication3.sample.utils.watcher.RecyclerViewItemWatcher
import com.example.myapplication3.sample.viewholder.posts.PostActionCallback
import com.example.myapplication3.sample.viewholder.posts.PostViewHolderExtras
import com.example.myapplication3.sample.viewholder.recommended.RecommendedFriendsViewHolderExtras
import com.example.myapplication3.sample.viewholder.recommended.UserActionCallback
import com.example.myapplication3.sample.viewholder.rooms.RoomActionCallback
import com.example.myapplication3.sample.viewholder.rooms.RoomsViewHolderExtras
import com.example.myapplication3.sample.viewholder.stories.StoriesViewHolderExtras
import com.example.myapplication3.sample.viewholder.stories.StoryActionCallback
import com.haizo.generaladapter.interfaces.LoadMoreListener
import com.haizo.generaladapter.kotlin.setupVertical
import com.haizo.generaladapter.listadapter.BlenderListAdapter
import com.haizo.generaladapter.listitems.MockListItem
import com.haizo.generaladapter.model.ListItem
import com.haizo.generaladapter.utils.ItemPaddingDecoration
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import java.util.LinkedList
import java.util.Queue
import java.util.concurrent.atomic.AtomicBoolean
import java.util.stream.Collectors.toList
import javax.inject.Inject
import kotlin.text.Typography.section

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    @Inject
    lateinit var postActionCallback: PostActionCallback

    @Inject
    lateinit var userActionCallback: UserActionCallback

    @Inject
    lateinit var storyActionCallback: StoryActionCallback

    @Inject
    lateinit var roomActionCallback: RoomActionCallback

    private val viewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setupRecyclerView()
        loadData()
    }

    //####################################################//
    //################ RecyclerView ######################//
    //####################################################//

    private val adapter: BlenderListAdapter by lazy {
        BlenderListAdapter(
            context = this,
            actionCallbacks = arrayOf(
                postActionCallback,
                userActionCallback,
                storyActionCallback,
                roomActionCallback
            )
        ).apply {
            setExtraParams(
                StoriesViewHolderExtras(storiesLoadMoreListener),
                RoomsViewHolderExtras(roomsLoadMoreListener),
                RecommendedFriendsViewHolderExtras(recommendedFriendsLoadMoreListener),
                PostViewHolderExtras(onDeletePostCallback = { adapter.removeItemFromList(it.id) })
            )
        }
    }

    private fun setupRecyclerView() {
        findViewById<RecyclerView>(R.id.recyclerView).let {
            it.setupVertical()
            it.adapter = adapter
            it.addItemDecoration(ItemPaddingDecoration(top = 8))
            RecyclerViewItemWatcher<Post>(it).let { watcher ->
                watcher.initialize()
                watcher.registerForExactSpentTimeOnEachItemView(this)
            }
            adapter.setupLoadMore(loadMoreListener = postsLoadMoreListener)
        }
    }

    //####################################################//
    //############### LoadMore Callbacks #################//
    //####################################################//

    private val postsLoadMoreListener = object : LoadMoreListener {
        override fun onLoadMore(nextPageNumber: Int, nextPagePayload: String?) {
            nextPagePayload?.let { it.toIntOrNull()?.let { nextPage -> viewModel.getPosts(nextPage) } }
        }

        override fun isShouldTriggerLoadMore(nextPageNumber: Int, nextPagePayload: String?): Boolean {
            return nextPagePayload != null
        }
    }

    private val storiesLoadMoreListener = object : LoadMoreListener {
        override fun onLoadMore(nextPageNumber: Int, nextPagePayload: String?) {
            nextPagePayload?.let { it.toIntOrNull()?.let { nextPage -> viewModel.getStories(nextPage) } }
        }

        override fun isShouldTriggerLoadMore(nextPageNumber: Int, nextPagePayload: String?): Boolean {
            return nextPagePayload != null
        }
    }

    private val roomsLoadMoreListener = object : LoadMoreListener {
        override fun onLoadMore(nextPageNumber: Int, nextPagePayload: String?) {
            nextPagePayload?.let { it.toIntOrNull()?.let { nextPage -> viewModel.getRooms(nextPage) } }
        }

        override fun isShouldTriggerLoadMore(nextPageNumber: Int, nextPagePayload: String?): Boolean {
            return nextPagePayload != null
        }
    }

    private val recommendedFriendsLoadMoreListener = object : LoadMoreListener {
        override fun onLoadMore(nextPageNumber: Int, nextPagePayload: String?) {
            nextPagePayload?.let { it.toIntOrNull()?.let { nextPage -> viewModel.getRecommendedFriends(nextPage) } }
        }

        override fun isShouldTriggerLoadMore(nextPageNumber: Int, nextPagePayload: String?): Boolean {
            return nextPagePayload != null
        }
    }

    //####################################################//
    //################### Load Data ######################//
    //####################################################//

    private fun loadData() {
        lifecycleScope.launch {
            viewModel.mainListFlow.collect { mainListWrapper ->
                adapter.submitListItems(mainListWrapper.list.toList(), mainListWrapper.nextPagePayload)
            }
        }
    }
}