package com.example.myapplication3.sample.listitem.story

import com.example.myapplication3.sample.viewholder.stories.STORIES_SECTION_VIEW_HOLDER_CONTRACT
import com.haizo.generaladapter.model.ListItem
import com.haizo.generaladapter.model.ListItemsContainer

data class StoriesListContainer constructor(
    var list: MutableList<StoryWrapperItem>,
    val nextPagePayload: String? = null,
    val isLoading: Boolean = false
) : ListItemsContainer<StoryWrapperItem> {

    override fun getInnerList() = list
    override fun itemUniqueIdentifier() = ITEM_UNIQUE_IDENTIFIER

    override fun areContentsTheSame(newItem: ListItem): Boolean {
        return newItem == this
    }
    override val viewHolderContract = STORIES_SECTION_VIEW_HOLDER_CONTRACT

    companion object {
        const val ITEM_UNIQUE_IDENTIFIER = "stories_container"
    }
}