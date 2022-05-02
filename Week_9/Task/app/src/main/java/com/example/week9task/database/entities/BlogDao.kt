package com.example.week9task.database.entities

import androidx.room.*
import com.example.week9task.database.entities.relations.PostWithComments

@Dao
interface BlogDao {

    @Query("SELECT * FROM posts")
    suspend fun getPosts(): List<Post>

    @Query("DELETE FROM posts")
    suspend fun emptyPosts()

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertPost(post: Post)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertComment(comment: Comment)

    @Transaction
    @Query("SELECT * FROM posts WHERE id = :postId")
    suspend fun getPostWithComments(postId: Int): PostWithComments
}
