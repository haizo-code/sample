package com.example.myapplication3.sample.listitem.story

import com.example.myapplication3.sample.model.Story
import com.example.myapplication3.sample.viewholder.stories.STORY_VIEW_HOLDER_CONTRACT
import com.haizo.generaladapter.model.ListItemWrapper
import com.haizo.generaladapter.model.ViewHolderContract

class StoryWrapperItem constructor(
    val story: Story
) : ListItemWrapper {

    override val viewHolderContract: ViewHolderContract
        get() = STORY_VIEW_HOLDER_CONTRACT

    override fun itemUniqueIdentifier(): String {
        return story.id
    }
}