package com.example.week9task.database.entities.relations

import androidx.room.Embedded
import androidx.room.Relation
import com.example.week9task.database.entities.Comment
import com.example.week9task.database.entities.Post

data class PostWithComments(
    @Embedded val post: Post,
    @Relation(
        parentColumn = "id",
        entityColumn = "postId"
    )
    val comments: List<Comment>
)
