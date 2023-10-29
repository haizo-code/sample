package com.example.myapplication3.sample.model

import java.util.UUID

data class User constructor(
    val id: String = UUID.randomUUID().toString(),
    val title: String = "",
    val username: String = "",
    val fullName: String = "",
    val profilePictureUrl: String = ""
)