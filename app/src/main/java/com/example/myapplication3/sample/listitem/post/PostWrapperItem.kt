package com.example.myapplication3.sample.listitem.post

import com.example.myapplication3.sample.model.Post
import com.example.myapplication3.sample.model.PostType
import com.example.myapplication3.sample.viewholder.posts.IMAGE_POST_HOLDER_CONTRACT
import com.example.myapplication3.sample.viewholder.posts.TEXT_POST_HOLDER_CONTRACT
import com.example.myapplication3.sample.viewholder.posts.VIDEO_POST_HOLDER_CONTRACT
import com.haizo.generaladapter.model.ListItem
import com.haizo.generaladapter.model.ListItemWrapper
import com.haizo.generaladapter.model.ViewHolderContract

class PostWrapperItem constructor(
    val post: Post
) : ListItemWrapper {

    override val viewHolderContract: ViewHolderContract
        get() = when (post.postType) {
            PostType.TEXT -> TEXT_POST_HOLDER_CONTRACT
            PostType.IMAGE -> IMAGE_POST_HOLDER_CONTRACT
            PostType.VIDEO -> VIDEO_POST_HOLDER_CONTRACT
        }

    override fun itemUniqueIdentifier(): String {
        return post.id
    }

    override fun areContentsTheSame(newItem: ListItem): Boolean {
        return this == newItem
    }
}