package com.example.week9task.networking

import com.example.week9task.database.entities.Post
import com.example.week9task.models.Comments
import com.example.week9task.models.Posts
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface AppApi {

    @GET("/posts")
    suspend fun getPosts(): Response<Posts>

    @POST("/posts")
    suspend fun savePost(@Body post: Post): Response<Post>

    @GET("/posts/{id}/comments/")
    suspend fun getPostComments(@Path("id") id: Int): Response<Comments>
}
