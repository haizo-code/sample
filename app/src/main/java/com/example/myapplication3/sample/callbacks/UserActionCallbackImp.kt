package com.example.myapplication3.sample.callbacks

import android.content.Context
import android.widget.Toast
import com.example.myapplication3.sample.model.User
import com.example.myapplication3.sample.viewholder.recommended.UserActionCallback
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class UserActionCallbackImp @Inject constructor(
    @ApplicationContext private val context: Context
) : UserActionCallback {

    override fun onUserClicked(user: User) {
        Toast.makeText(context, "User -> ${user.id} ", Toast.LENGTH_SHORT).show()
    }
}