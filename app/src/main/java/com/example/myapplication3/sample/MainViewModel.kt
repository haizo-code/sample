package com.example.myapplication3.sample

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myapplication3.MainListWrapper
import com.example.myapplication3.sample.listitem.post.PostWrapperItem
import com.example.myapplication3.sample.listitem.post.PostsListContainer
import com.example.myapplication3.sample.listitem.recommended.RecommendedFriendWrapperItem
import com.example.myapplication3.sample.listitem.recommended.RecommendedFriendsListContainer
import com.example.myapplication3.sample.listitem.room.RoomWrapperItem
import com.example.myapplication3.sample.listitem.room.RoomsListContainer
import com.example.myapplication3.sample.listitem.story.StoriesListContainer
import com.example.myapplication3.sample.listitem.story.StoryWrapperItem
import com.haizo.generaladapter.model.ListItem
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val mainRepository: MainRepository
) : ViewModel() {

    private val _mainListStateFlow = MutableStateFlow(MainListWrapper(emptyList()))
    val mainListFlow: StateFlow<MainListWrapper> = _mainListStateFlow.asStateFlow()

    private val storiesFlow: Flow<StoriesListContainer> = mainRepository.getStories()
        .map { responseWrapper ->
            val response = responseWrapper.response
            val wrappers = response.list.map { StoryWrapperItem(it) }.toMutableList()
            StoriesListContainer(wrappers, response.nextPage, responseWrapper.isLoading)
        }

    private val roomsFlow: Flow<RoomsListContainer> = mainRepository.getRooms()
        .map { responseWrapper ->
            val response = responseWrapper.response
            val wrappers = response.list.map { RoomWrapperItem(it) }.toMutableList()
            RoomsListContainer(wrappers, response.nextPage, responseWrapper.isLoading)
        }

    private val recommendedFriendsFlow: Flow<RecommendedFriendsListContainer> = mainRepository.getRecommendedFriends()
        .map { responseWrapper ->
            val response = responseWrapper.response
            val wrappers = response.list.map { RecommendedFriendWrapperItem(it) }.toMutableList()
            RecommendedFriendsListContainer(wrappers, response.nextPage, responseWrapper.isLoading)
        }

    private val postsFlow: Flow<PostsListContainer> = mainRepository.getPosts()
        .map { responseWrapper ->
            val response = responseWrapper.response
            val listItems = response.list.map { PostWrapperItem(it) }.toMutableList()
            PostsListContainer(listItems, response.nextPage, responseWrapper.isLoading)
        }

    private var postsNextPage: String? = null

    init {
        combine(
            storiesFlow,
            roomsFlow,
            recommendedFriendsFlow,
            postsFlow
        ) { stories: StoriesListContainer,
            rooms: RoomsListContainer,
            recommendedFriends: RecommendedFriendsListContainer,
            posts: PostsListContainer ->
            {
                val mainList = ArrayList<ListItem>()
                mainList.apply {
                    add(stories)
                    add(rooms)
                    add(recommendedFriends)
                    addAll(posts.list)
                    postsNextPage = posts.nextPagePayload
                }
            }
        }.onEach { combinedData ->
            _mainListStateFlow.value = MainListWrapper(combinedData.invoke(), postsNextPage)
        }.launchIn(viewModelScope)
    }

    fun getStories(nextPage: Int) {
        mainRepository.getStories(nextPage)
            .onEach { responseWrapper ->
                _mainListStateFlow.update { currentData ->
                    // Combine the new items with the existing data
                    val container = currentData.list.filterIsInstance<StoriesListContainer>().first()
                    val newWrappedItems = responseWrapper.response.list.map { StoryWrapperItem(it) }.toMutableList()
                    val combinedList = container.list.apply { addAll(newWrappedItems) }
                    val updatedContainer = StoriesListContainer(combinedList, responseWrapper.response.nextPage)
                    val list = currentData.list.toMutableList().apply { set(indexOf(container), updatedContainer) }
                    MainListWrapper(list, responseWrapper.response.nextPage, responseWrapper.isLoading)
                }
            }
            .launchIn(viewModelScope)
    }

    fun getRooms(nextPage: Int) {
        mainRepository.getRooms(nextPage)
            .onEach { responseWrapper ->
                _mainListStateFlow.update { currentData ->
                    // Combine the new items with the existing data
                    val newWrappedItems = responseWrapper.response.list.map { RoomWrapperItem(it) }.toMutableList()
                    val container = currentData.list.filterIsInstance<RoomsListContainer>().first()
                    val combinedList = container.list.apply { addAll(newWrappedItems) }
                    val updatedContainer = RoomsListContainer(combinedList, responseWrapper.response.nextPage)
                    val list = currentData.list.toMutableList().apply { set(indexOf(container), updatedContainer) }
                    MainListWrapper(list, responseWrapper.response.nextPage, responseWrapper.isLoading)
                }
            }
            .launchIn(viewModelScope)
    }

    fun getRecommendedFriends(nextPage: Int) {
        mainRepository.getRecommendedFriends(nextPage)
            .onEach { responseWrapper ->
                _mainListStateFlow.update { currentData ->
                    // Combine the new items with the existing data
                    val container = currentData.list.filterIsInstance<RecommendedFriendsListContainer>().first()
                    val newWrappedItems =
                        responseWrapper.response.list.map { RecommendedFriendWrapperItem(it) }.toMutableList()
                    val combinedList = container.list.apply { addAll(newWrappedItems) }
                    val updatedContainer =
                        RecommendedFriendsListContainer(combinedList, responseWrapper.response.nextPage)
                    val list = currentData.list.toMutableList().apply { set(indexOf(container), updatedContainer) }
                    MainListWrapper(list, responseWrapper.response.nextPage, responseWrapper.isLoading)
                }
            }
            .launchIn(viewModelScope)
    }

    fun getPosts(nextPage: Int) {
        viewModelScope.launch {
            mainRepository.getPosts(nextPage).collect { responseWrapper ->
                val newListItems = responseWrapper.response.list.map { PostWrapperItem(it) }
                _mainListStateFlow.update { currentData ->
                    val list = currentData.list.toMutableList().apply { addAll(newListItems) }
                    MainListWrapper(list, responseWrapper.response.nextPage, responseWrapper.isLoading)
                }
            }
        }
    }

    fun deleteItem(item: ListItem) {
        viewModelScope.launch {
            _mainListStateFlow.update {
                val list = it.list.toMutableList()
                list.remove(item)
                MainListWrapper(list)
            }
        }

    }
}