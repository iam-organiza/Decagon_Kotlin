package com.example.week9task.models

import java.io.Serializable

data class PostsItem(
    val body: String,
    val id: Int,
    val title: String,
    val userId: Int
): Serializable