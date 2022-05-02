package com.example.week9task.database.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable

@Entity(tableName = "posts")
data class Post(
    @PrimaryKey(autoGenerate = true)
    val pid: Int? = null,
    val body: String,
    val id: Int,
    val title: String,
    val userId: Int
): Serializable
