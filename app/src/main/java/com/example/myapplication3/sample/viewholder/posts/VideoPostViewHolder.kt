package com.example.myapplication3.sample.viewholder.posts

import com.example.myapplication3.databinding.RowPostBinding
import com.example.myapplication3.sample.listitem.post.PostWrapperItem

//####################################################//
//#################### Contract ######################//
//####################################################//

internal val VIDEO_POST_HOLDER_CONTRACT = PostContractHelper.getContract(VideoPostViewHolder::class.java)

//####################################################//
//################## ViewHolder ######################//
//####################################################//

internal class VideoPostViewHolder constructor(
    private val binding: RowPostBinding,
    private val callback: PostActionCallback,
    private val extras: PostViewHolderExtras,
) : BasePostViewHolder(binding, callback, extras) {

    override fun onBind(listItem: PostWrapperItem) {
        super.onBind(listItem)
        binding.vsVideoPost.viewStub?.inflate()
    }
}