package com.example.week9task.viewmodels

import android.content.Context
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.week9task.database.entities.BlogDB
import com.example.week9task.database.entities.Post
import com.example.week9task.repo.Repo
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

@HiltViewModel
class MainActivityViewModel @Inject constructor(
    @ApplicationContext val context: Context
) : ViewModel() {

    var dbPosts = mutableListOf<Post>()
    private val repo = Repo()
    var posts = MutableLiveData<List<Post>>()
    private val dao = BlogDB.getInstance(context).blogDao

    init {
        loadFirstData()
    }

    private fun insertPost(post: Post) {
        viewModelScope.launch {
            dao.insertPost(post)
        }
    }

    fun emptyPostsTable() {
        viewModelScope.launch {
            dao.emptyPosts()
        }
    }

    fun savePost(post: Post): Job {
        val self = this
        return viewModelScope.launch {
            val response = try {
                repo.savePost(post)
            } catch (e: IOException) {
                Log.e("io", "IOException")
                return@launch
            } catch (e: HttpException) {
                Log.e("http", "HttpException")
                return@launch
            }

            val result = response.body()
            if (response.isSuccessful && result != null) {
                val post = Post(
                    body = result.body,
                    id = result.id,
                    title = result.title,
                    userId = result.userId
                )
                dbPosts.add(post)
                insertPost(post)

                self.posts.apply {
                    value = dbPosts
                }
            }
        }
    }

    fun loadFirstData(){
        viewModelScope.launch {
            val response = try {
                repo.getPosts()
            } catch (e: IOException) {
                Log.e("io", "IOException")
                return@launch
            } catch (e: HttpException) {
                Log.e("http", "HttpException")
                return@launch
            }

            val results = response.body()
            if (response.isSuccessful && results != null) {
                for (result in results) {
                    val post = Post(
                        body = result.body,
                        id = result.id,
                        title = result.title,
                        userId = result.userId
                    )
                    dao.insertPost(post)
                    dbPosts.add(post)
                }
                posts.value = dbPosts
            }
        }

    }

}