package com.example.week9task.repo

import com.example.week9task.config.RetrofitInstance
import com.example.week9task.database.entities.Post

class Repo {

    suspend fun getPosts() = RetrofitInstance.api.getPosts()

    suspend fun savePost(post: Post) = RetrofitInstance.api.savePost(post)

    suspend fun getPostComments(id: Int) = RetrofitInstance.api.getPostComments(id)
}
