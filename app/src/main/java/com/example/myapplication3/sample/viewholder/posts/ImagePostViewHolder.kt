package com.example.myapplication3.sample.viewholder.posts

import com.bumptech.glide.Glide
import com.example.myapplication3.R
import com.example.myapplication3.databinding.LayoutPostImageBinding
import com.example.myapplication3.databinding.RowPostBinding
import com.example.myapplication3.sample.listitem.post.PostWrapperItem
import com.example.myapplication3.sample.utils.GlideImageUtils

//####################################################//
//#################### Contract ######################//
//####################################################//

internal val IMAGE_POST_HOLDER_CONTRACT = PostContractHelper.getContract(ImagePostViewHolder::class.java)

//####################################################//
//################## ViewHolder ######################//
//####################################################//

internal class ImagePostViewHolder constructor(
    private val binding: RowPostBinding,
    private val callback: PostActionCallback,
    private val extras: PostViewHolderExtras,
) : BasePostViewHolder(binding, callback, extras) {

    private var innerBinding: LayoutPostImageBinding

    init {
        binding.vsImagePost.viewStub?.inflate()
        innerBinding = binding.vsImagePost.binding as LayoutPostImageBinding
    }

    override fun onBind(listItem: PostWrapperItem) {
        super.onBind(listItem)
        val url = "https://picsum.photos/id/$bindingAdapterPositionCompat/800/600"
        GlideImageUtils.setImage(context, imageToLoad = url, imageView = innerBinding.ivImage)
    }
}