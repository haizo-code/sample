package com.example.myapplication3.sample.viewholder.rooms

import com.example.myapplication3.R
import com.example.myapplication3.databinding.RowRoomBinding
import com.example.myapplication3.sample.listitem.room.RoomWrapperItem
import com.example.myapplication3.sample.model.Room
import com.haizo.generaladapter.interfaces.BaseActionCallback
import com.haizo.generaladapter.model.ViewHolderContract
import com.haizo.generaladapter.viewholders.BaseBindingViewHolder

//####################################################//
//#################### Contract ######################//
//####################################################//

internal val ROOM_VIEW_HOLDER_CONTRACT = ViewHolderContract(
    viewHolderClass = RoomViewHolder::class.java,
    layoutResId = R.layout.row_room,
    callbackClass = RoomActionCallback::class.java
)

//####################################################//
//#################### Callback ######################//
//####################################################//

interface RoomActionCallback : BaseActionCallback {
    fun onRoomClicked(room: Room)
}

//####################################################//
//################## ViewHolder ######################//
//####################################################//

internal open class RoomViewHolder constructor(
    private val binding: RowRoomBinding,
    private val callback: RoomActionCallback,
) : BaseBindingViewHolder<RoomWrapperItem>(binding, callback) {

    init {
        initListeners()
    }

    override fun onBind(listItem: RoomWrapperItem) {
        binding.room = listItem.room
    }

    private fun initListeners(){
        itemView.setOnClickListener {
            callback.onRoomClicked(listItem.room)
        }
    }
}