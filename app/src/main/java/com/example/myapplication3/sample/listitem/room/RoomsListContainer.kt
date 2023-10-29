package com.example.myapplication3.sample.listitem.room

import com.example.myapplication3.sample.viewholder.rooms.ROOMS_SECTION_VIEW_HOLDER_CONTRACT
import com.haizo.generaladapter.model.ListItemsContainer

class RoomsListContainer constructor(
    var list: MutableList<RoomWrapperItem>,
    val nextPagePayload: String? = null,
    val isLoading: Boolean = false,
) : ListItemsContainer<RoomWrapperItem> {

    override fun getInnerList() = list
    override fun itemUniqueIdentifier() = ITEM_UNIQUE_IDENTIFIER
    override val viewHolderContract = ROOMS_SECTION_VIEW_HOLDER_CONTRACT

    companion object {
        const val ITEM_UNIQUE_IDENTIFIER = "rooms_container"
    }
}