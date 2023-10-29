package com.example.myapplication3.sample.viewholder.stories

import com.example.myapplication3.R
import com.example.myapplication3.databinding.RowStoryBinding
import com.example.myapplication3.sample.listitem.story.StoryWrapperItem
import com.example.myapplication3.sample.model.Story
import com.haizo.generaladapter.interfaces.BaseActionCallback
import com.haizo.generaladapter.model.ViewHolderContract
import com.haizo.generaladapter.viewholders.BaseBindingViewHolder

//####################################################//
//#################### Contract ######################//
//####################################################//

internal val STORY_VIEW_HOLDER_CONTRACT = ViewHolderContract(
    viewHolderClass = StoryViewHolder::class.java,
    layoutResId = R.layout.row_story,
    callbackClass = StoryActionCallback::class.java
)

//####################################################//
//#################### Callback ######################//
//####################################################//

interface StoryActionCallback : BaseActionCallback {
    fun onStoryClicked(story: Story)
}

//####################################################//
//################## ViewHolder ######################//
//####################################################//

internal open class StoryViewHolder constructor(
    private val binding: RowStoryBinding,
    private val callback: StoryActionCallback,
) : BaseBindingViewHolder<StoryWrapperItem>(binding, callback) {

    init {
        initListeners()
    }

    override fun onBind(listItem: StoryWrapperItem) {
        binding.story = listItem.story
    }

    private fun initListeners(){
        itemView.setOnClickListener {
            callback.onStoryClicked(listItem.story)
        }
    }
}