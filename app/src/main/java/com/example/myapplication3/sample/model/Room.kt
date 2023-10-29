package com.example.myapplication3.sample.model

import java.util.UUID

data class Room constructor(
    val id: String = UUID.randomUUID().toString(),
    val title: String
)