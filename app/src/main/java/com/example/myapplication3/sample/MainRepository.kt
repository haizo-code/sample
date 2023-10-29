package com.example.myapplication3.sample

import com.example.myapplication3.sample.model.Post
import com.example.myapplication3.sample.model.PostGeneratorHelper
import com.example.myapplication3.sample.model.PostType
import com.example.myapplication3.sample.model.Room
import com.example.myapplication3.sample.model.Story
import com.example.myapplication3.sample.model.User
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class MainRepository @Inject constructor() {

    private val pageSize = 15

    private fun <T> getPagedCollection(
        page: Int = 0,
        dummyDelayInMills: Long,
        fetchFunction: suspend (Int) -> List<T>
    ): Flow<ResponseWrapper<PagedCollectionResponse<T>>> = flow {
        if (page == 0) {
            emit(ResponseWrapper(PagedCollectionResponse(emptyList()), true))
        }
        delay(dummyDelayInMills)

        val dummyPageNumber = page * pageSize
        val list = fetchFunction(dummyPageNumber)

        val nextPage = "${page + 1}".takeIf { page <= 2 }
        emit(ResponseWrapper(PagedCollectionResponse(list, nextPage), false))
    }

    fun getStories(page: Int = 0): Flow<ResponseWrapper<PagedCollectionResponse<Story>>> {
        return getPagedCollection(page, 1000) { dummyPageNumber ->
            val list = mutableListOf<Story>()
            for (i in dummyPageNumber until dummyPageNumber + pageSize) list.add(Story(title = "Story $i"))
            list
        }
    }

    fun getRooms(page: Int = 0): Flow<ResponseWrapper<PagedCollectionResponse<Room>>> {
        return getPagedCollection(page, 1500) { dummyPageNumber ->
            val list = mutableListOf<Room>()
            for (i in dummyPageNumber until dummyPageNumber + pageSize) list.add(Room(title = "Room #$i - Page: $page"))
            list
        }
    }

    fun getRecommendedFriends(page: Int = 0): Flow<ResponseWrapper<PagedCollectionResponse<User>>> {
        return getPagedCollection(page, 2000) { dummyPageNumber ->
            val list = mutableListOf<User>()
            for (i in dummyPageNumber until dummyPageNumber + pageSize) list.add(User(title = "User #$i - Page: $page"))
            list
        }
    }

    fun getPosts(page: Int = 0): Flow<ResponseWrapper<PagedCollectionResponse<Post>>> {
        return getPagedCollection(page, 2500) { dummyPageNumber ->
            val list = mutableListOf<Post>()
            for (i in dummyPageNumber until dummyPageNumber + pageSize) {
                list.add(PostGeneratorHelper.generateRandomPost(PostType.values()[i % PostType.values().size]))
            }
            list
        }
    }
}

class ResponseWrapper<T> constructor(
    val response: T,
    val isLoading: Boolean
)

class PagedCollectionResponse<T> constructor(
    val list: List<T>,
    val nextPage: String? = null,
)