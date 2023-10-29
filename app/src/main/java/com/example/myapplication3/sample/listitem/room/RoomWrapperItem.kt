package com.example.myapplication3.sample.listitem.room

import com.example.myapplication3.sample.model.Room
import com.example.myapplication3.sample.viewholder.rooms.ROOM_VIEW_HOLDER_CONTRACT
import com.haizo.generaladapter.model.ListItemWrapper
import com.haizo.generaladapter.model.ViewHolderContract

class RoomWrapperItem constructor(
    val room: Room
) : ListItemWrapper {

    override val viewHolderContract: ViewHolderContract
        get() = ROOM_VIEW_HOLDER_CONTRACT

    override fun itemUniqueIdentifier(): String {
        return room.id
    }
}