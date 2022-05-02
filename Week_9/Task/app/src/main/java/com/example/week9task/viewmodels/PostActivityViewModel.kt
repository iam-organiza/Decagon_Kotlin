package com.example.week9task.viewmodels

import android.app.Application
import android.content.Context
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.week9task.database.entities.BlogDB
import com.example.week9task.database.entities.Comment
import com.example.week9task.database.entities.Post
import com.example.week9task.database.entities.relations.PostWithComments
import com.example.week9task.models.Comments
import com.example.week9task.models.CommentsItem
import com.example.week9task.repo.Repo
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

@HiltViewModel
class PostActivityViewModel @Inject constructor(
    context: Application,
    val post: Post
): AndroidViewModel(context) {
    private val repo = Repo()
    private val postComments = mutableListOf<Comment>()
    var comments = MutableLiveData<PostWithComments>()
    private val dao = BlogDB.getInstance(context.applicationContext).blogDao

    init {
        viewModelScope.launch {
            val response = try {
                repo.getPostComments(post.id)
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
                    val comment = Comment(
                        body = result.body,
                        email = result.email,
                        id = result.id,
                        name = result.name,
                        postId = result.id
                    )
                    postComments.add(comment)
                    dao.insertComment(comment)
                }

                comments.value = PostWithComments(
                    post = post,
                    comments = postComments
                )
            }
        }
    }

}