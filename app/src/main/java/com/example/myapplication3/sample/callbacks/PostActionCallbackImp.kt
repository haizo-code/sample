package com.example.myapplication3.sample.callbacks

import android.content.Context
import android.widget.Toast
import com.example.myapplication3.sample.model.Post
import com.example.myapplication3.sample.viewholder.posts.PostActionCallback
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class PostActionCallbackImp @Inject constructor(
    @ApplicationContext private val context: Context
) : PostActionCallback {

    override fun onLikeClick(post: Post) {
        Toast.makeText(context, "Like: PostId -> ${post.id} ", Toast.LENGTH_SHORT).show()
    }

    override fun onCommentClicked(post: Post) {
        Toast.makeText(context, "Comment: PostId -> ${post.id} ", Toast.LENGTH_SHORT).show()
    }

    override fun onShareClicked(post: Post) {
        Toast.makeText(context, "Share: PostId -> ${post.id} ", Toast.LENGTH_SHORT).show()
    }

    override fun onDeletePost(post: Post, onDeletePostCallback: (Post) -> Unit) {
        Toast.makeText(context, "Remove: PostId -> ${post.id} ", Toast.LENGTH_SHORT).show()
        onDeletePostCallback.invoke(post)
    }
}