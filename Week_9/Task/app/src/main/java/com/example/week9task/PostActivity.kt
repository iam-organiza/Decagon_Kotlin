package com.example.week9task

import android.os.Bundle
import android.view.View
import android.widget.ProgressBar
import android.widget.TextView
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.cardview.widget.CardView
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.week9task.adapters.BlogPostCommentsAdapter
import com.example.week9task.database.entities.Post
import com.example.week9task.databinding.ActivityPostBinding
import com.example.week9task.viewmodels.PostActivityViewModel

class PostActivity : AppCompatActivity() {

    private lateinit var binding: ActivityPostBinding

    private lateinit var post: Post
    private lateinit var postActivityViewModel: PostActivityViewModel

    private val postActivityViewModel2 : PostActivityViewModel by viewModels()

    private lateinit var loader: ProgressBar
    private lateinit var rvblogPostComments: RecyclerView
    private lateinit var commentsCountView: TextView
    private lateinit var commentsCountCardView: CardView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPostBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // postActivityViewModel = ViewModelProvider(this)[PostActivityViewModel::class.java]

        loader = binding.commentsProgressBar
        rvblogPostComments = binding.blogPostComments
        commentsCountView = binding.commentsCount
        commentsCountCardView = binding.commentsCountCard

        post = intent.getSerializableExtra("postItem") as Post
        binding.appPostToolBar.title = "Post #${post.id}"

        loader("show")
        postActivityViewModel = PostActivityViewModel(application, post)

        displayPost(post)
        displayComments(post.id)

    }

    private fun displayPost(postsItem: Post) {
        val postsItemTitleString = getString(R.string.post_item_title, postsItem.title)
        binding.postItemTitle.text = postsItemTitleString

        val postItemBodyString = getString(R.string.post_item_body, postsItem.body)
        binding.postItemBody.text = postItemBodyString
    }

    private fun displayComments(id: Int) {
        commentsCountCardView.visibility = View.GONE
        postActivityViewModel.comments.observe(this) {

            val comments = it
            val adapter = BlogPostCommentsAdapter(comments.comments)
            val commentsCountViewString = getString(R.string.comments_count, comments.comments.size)
            commentsCountView.text = commentsCountViewString
            rvblogPostComments.adapter = adapter

            rvblogPostComments.layoutManager = LinearLayoutManager(this)
            loader("hide")
            commentsCountCardView.visibility = View.VISIBLE

        }
    }

    private fun loader(keyword: String) {
        when (keyword.lowercase()) {
            "hide" -> {
                loader.visibility = View.GONE
            }
            "show" -> {
                loader.visibility = View.VISIBLE
            }
            else -> {
                loader.visibility = View.GONE
            }
        }
    }
}