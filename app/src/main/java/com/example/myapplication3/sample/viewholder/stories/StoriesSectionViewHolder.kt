package com.example.myapplication3.sample.viewholder.stories

import com.example.myapplication3.R
import com.example.myapplication3.databinding.RowStoriesSectionBinding
import com.example.myapplication3.sample.listitem.story.StoriesListContainer
import com.haizo.generaladapter.MainViewHolderContracts
import com.haizo.generaladapter.interfaces.ContainerViewHolder
import com.haizo.generaladapter.interfaces.LoadMoreListener
import com.haizo.generaladapter.interfaces.ViewHolderExtras
import com.haizo.generaladapter.kotlin.setupHorizontal
import com.haizo.generaladapter.listadapter.BaseRecyclerListAdapter
import com.haizo.generaladapter.listadapter.BlenderListAdapter
import com.haizo.generaladapter.listitems.MockLoadingListItem
import com.haizo.generaladapter.model.ViewHolderContract
import com.haizo.generaladapter.utils.ItemPaddingDecoration
import com.haizo.generaladapter.viewholders.BaseBindingViewHolder

//####################################################//
//#################### Contract ######################//
//####################################################//

internal val STORIES_SECTION_VIEW_HOLDER_CONTRACT = ViewHolderContract(
    viewHolderClass = StoriesSectionViewHolder::class.java,
    layoutResId = R.layout.row_stories_section,
    callbackClass = StoryActionCallback::class.java,
    extrasClass = StoriesViewHolderExtras::class.java
)

//####################################################//
//##################### Extras #######################//
//####################################################//

class StoriesViewHolderExtras constructor(
    val loadMoreListener: LoadMoreListener
) : ViewHolderExtras

//####################################################//
//################## ViewHolder ######################//
//####################################################//

internal open class StoriesSectionViewHolder constructor(
    private val binding: RowStoriesSectionBinding,
    private val callback: StoryActionCallback,
    private val extras: StoriesViewHolderExtras,
) : BaseBindingViewHolder<StoriesListContainer>(binding, callback), ContainerViewHolder {

    private val adapter: BlenderListAdapter by lazy { BlenderListAdapter(context, callback) }

    init {
        setupRecyclerView()
    }

    override fun onBind(listItem: StoriesListContainer) {
        if (listItem.isLoading) {
            adapter.addLoadingItem()
        } else {
            adapter.submitListItems(listItem.getInnerList(), listItem.nextPagePayload)
        }
    }

    private fun setupRecyclerView() {
        binding.recyclerView.let {
            it.setupHorizontal()
            it.adapter = adapter
        }
        adapter.setupLoadMore(loadMoreListener = extras.loadMoreListener)
        adapter.setLoadingListItem(StoriesSectionLoadingItem())
    }

    override fun getInnerAdapter(): BaseRecyclerListAdapter {
        return adapter
    }
}