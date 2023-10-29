package com.example.myapplication3.sample.model

import java.util.UUID

data class Story constructor(
    val id: String = UUID.randomUUID().toString(),
    val title: String
)