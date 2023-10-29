package com.example.myapplication3.sample.listitem.recommended

import com.example.myapplication3.sample.viewholder.recommended.COMMENDED_FRIENDS_SECTION_VIEW_HOLDER_CONTRACT
import com.haizo.generaladapter.model.ListItemsContainer

class RecommendedFriendsListContainer constructor(
    var list: MutableList<RecommendedFriendWrapperItem>,
    val nextPagePayload: String? = null,
    val isLoading: Boolean = false,
) : ListItemsContainer<RecommendedFriendWrapperItem> {

    override fun getInnerList() = list
    override fun itemUniqueIdentifier() = ITEM_UNIQUE_IDENTIFIER
    override val viewHolderContract = COMMENDED_FRIENDS_SECTION_VIEW_HOLDER_CONTRACT

    companion object {
        const val ITEM_UNIQUE_IDENTIFIER = "recommended_friends_container"
    }
}