package com.example.myapplication3.sample.di

import com.example.myapplication3.sample.callbacks.PostActionCallbackImp
import com.example.myapplication3.sample.callbacks.RoomActionCallbackImp
import com.example.myapplication3.sample.callbacks.StoryActionCallbackImp
import com.example.myapplication3.sample.callbacks.UserActionCallbackImp
import com.example.myapplication3.sample.viewholder.posts.PostActionCallback
import com.example.myapplication3.sample.viewholder.recommended.UserActionCallback
import com.example.myapplication3.sample.viewholder.rooms.RoomActionCallback
import com.example.myapplication3.sample.viewholder.stories.StoryActionCallback
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent

@Module
@InstallIn(ActivityComponent::class)
abstract class ActionCallbacksModule {

    @Binds
    internal abstract fun bindPostActionCallbackImp(imp: PostActionCallbackImp): PostActionCallback

    @Binds
    internal abstract fun bindUserActionCallbackImp(imp: UserActionCallbackImp): UserActionCallback

    @Binds
    internal abstract fun bindRoomActionCallbackImp(imp: RoomActionCallbackImp): RoomActionCallback

    @Binds
    internal abstract fun bindStoryActionCallbackImp(imp: StoryActionCallbackImp): StoryActionCallback
}