package com.example.myapplication3.sample.listitem.post

class PostsListContainer constructor(
    val list: MutableList<PostWrapperItem>,
    val nextPagePayload: String? = null,
    val isLoading: Boolean = false,
)