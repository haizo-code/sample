package com.example.myapplication3

import com.haizo.generaladapter.model.ListItem

class MainListWrapper constructor(
    val list: List<ListItem>,
    val nextPagePayload: String? = null,
    val isLoading: Boolean = false,
)