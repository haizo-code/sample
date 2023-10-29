package com.example.myapplication3.sample.callbacks

import android.content.Context
import android.widget.Toast
import com.example.myapplication3.sample.model.Story
import com.example.myapplication3.sample.viewholder.stories.StoryActionCallback
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class StoryActionCallbackImp @Inject constructor(
    @ApplicationContext private val context: Context
) : StoryActionCallback {

    override fun onStoryClicked(story: Story) {
        Toast.makeText(context, "Story -> ${story.id} ", Toast.LENGTH_SHORT).show()
    }
}