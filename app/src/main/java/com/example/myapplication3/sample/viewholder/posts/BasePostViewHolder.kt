package com.example.myapplication3.sample.viewholder.posts

import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication3.R
import com.example.myapplication3.databinding.RowPostBinding
import com.example.myapplication3.sample.listitem.post.PostWrapperItem
import com.example.myapplication3.sample.model.Post
import com.example.myapplication3.sample.utils.Utils
import com.example.myapplication3.sample.utils.watcher.RecyclerViewItemTimeSpentCallback
import com.example.myapplication3.sample.utils.watcher.RecyclerViewItemViewCallback
import com.haizo.generaladapter.interfaces.BaseActionCallback
import com.haizo.generaladapter.interfaces.ViewHolderExtras
import com.haizo.generaladapter.model.ViewHolderContract
import com.haizo.generaladapter.viewholders.BaseBindingViewHolder

object PostContractHelper {
    fun getContract(viewHolderClass: Class<out RecyclerView.ViewHolder>): ViewHolderContract {
        return ViewHolderContract(
            viewHolderClass = viewHolderClass,
            layoutResId = R.layout.row_post,
            callbackClass = PostActionCallback::class.java,
            extrasClass = PostViewHolderExtras::class.java
        )
    }
}

//####################################################//
//#################### Callback ######################//
//####################################################//

interface PostActionCallback : BaseActionCallback {
    fun onLikeClick(post: Post)
    fun onCommentClicked(post: Post)
    fun onShareClicked(post: Post)
    fun onDeletePost(post: Post, onDeletePostCallback: (Post) -> Unit)
}

//####################################################//
//##################### Extras #######################//
//####################################################//

class PostViewHolderExtras constructor(
    val onDeletePostCallback: (Post) -> Unit
) : ViewHolderExtras

//####################################################//
//################## ViewHolder ######################//
//####################################################//

internal open class BasePostViewHolder constructor(
    private val binding: RowPostBinding,
    private val callback: PostActionCallback,
    private val extras: PostViewHolderExtras,
) : BaseBindingViewHolder<PostWrapperItem>(binding, callback),
    RecyclerViewItemTimeSpentCallback,
    RecyclerViewItemViewCallback<PostWrapperItem> {

    init {
        initListeners()
    }

    private fun initListeners() {
        binding.footerBinding.tvLike.setOnClickListener { callback.onLikeClick(listItem.post) }
        binding.footerBinding.tvComment.setOnClickListener { callback.onCommentClicked(listItem.post) }
        binding.footerBinding.tvShare.setOnClickListener { callback.onShareClicked(listItem.post) }
        binding.layoutPostHeaderBinding.tvDelete.setOnClickListener {
            callback.onDeletePost(listItem.post, extras.onDeletePostCallback)
        }
    }

    override fun onBind(listItem: PostWrapperItem) {
        binding.post = listItem.post
    }

    override fun getItemUniqueIdentifier(): String {
        return listItem.itemUniqueIdentifier()
    }

    override fun onNewTimeSpent(totalTimeSpentInMillis: Long) {
        val timeString =
            Utils.getTimeString(totalTimeSpentInMillis, isShowHoursEventItsZero = false, isShowMillis = true)
        binding.layoutPostHeaderBinding.tvDuration.text = timeString
    }

    override fun getViewedItem(): PostWrapperItem? {
        return listItem
    }

    override fun onViewFocused(isFocused: Boolean) {
        binding.layoutPostHeaderBinding.tvDuration.isActivated = isFocused
        binding.contentFL.isSelected = isFocused
    }
}