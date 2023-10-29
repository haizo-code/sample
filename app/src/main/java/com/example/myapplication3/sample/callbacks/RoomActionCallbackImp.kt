package com.example.myapplication3.sample.callbacks

import android.content.Context
import android.widget.Toast
import com.example.myapplication3.sample.model.Room
import com.example.myapplication3.sample.viewholder.rooms.RoomActionCallback
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class RoomActionCallbackImp @Inject constructor(
    @ApplicationContext private val context: Context
) : RoomActionCallback {

    override fun onRoomClicked(room: Room) {
        Toast.makeText(context, "Room -> ${room.id} ", Toast.LENGTH_SHORT).show()
    }
}