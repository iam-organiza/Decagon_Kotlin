package com.example.week9task.database.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "comments")
data class Comment(
    @PrimaryKey(autoGenerate = true)
    val cid: Int? = null,
    val body: String,
    val email: String,
    val id: Int,
    val name: String,
    val postId: Int
)
