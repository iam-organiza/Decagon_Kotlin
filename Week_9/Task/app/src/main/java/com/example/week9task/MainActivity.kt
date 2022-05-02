package com.example.week9task

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Button
import android.widget.ProgressBar
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.week9task.adapters.BlogPostsAdapter
import com.example.week9task.database.entities.Post
import com.example.week9task.databinding.ActivityMainBinding
import com.example.week9task.models.PostsItem
import com.example.week9task.viewmodels.MainActivityViewModel
import com.google.android.material.textfield.TextInputEditText

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var mainActivityViewModel: MainActivityViewModel

    private lateinit var loader: ProgressBar
    private lateinit var rvPosts: RecyclerView
    private lateinit var dialogAddPostView: View
    private var postsLastId: Int = 0

    private lateinit var dialog: AlertDialog
    private val linearLayoutManager = LinearLayoutManager(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
//        mainActivityViewModel = ViewModelProvider(this)[MainActivityViewModel::class.java]

        loader = binding.progressBar
        loader("show")
        mainActivityViewModel = MainActivityViewModel(this)

        mainActivityViewModel.emptyPostsTable()

        rvPosts = binding.blogPosts
        displayPosts()

        binding.floatingActionScrollToBottom.setOnClickListener {
            scrollToBottom()
        }

        binding.floatingActionScrollToTop.setOnClickListener {
            scrollToTop()
        }

        binding.appToolBar.findViewById<View>(R.id.addPostMenuItem).setOnClickListener {
            addBlogPost()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        super.onCreateOptionsMenu(menu)
        menuInflater.inflate(R.menu.menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.addPostMenuItem) {
            addBlogPost()
        }

        return true
    }

    private fun addBlogPost() {
        val builder = AlertDialog.Builder(this)
        val inflater = this.layoutInflater
        dialogAddPostView = inflater.inflate(R.layout.add_new_post, null)
        builder.setView(dialogAddPostView)
        dialog = builder.create()

        dialogAddPostView.findViewById<Button>(R.id.savePost)?.setOnClickListener {
            submitPost()
        }

        dialogAddPostView.findViewById<Button>(R.id.cancelAddPostDialog)?.setOnClickListener {
            dialog.cancel()
        }
        dialog.show()
    }

    private fun scrollToBottom() {
        linearLayoutManager.scrollToPosition(postsLastId - 2)
    }

    private fun scrollToTop() {
        linearLayoutManager.scrollToPosition(0)
    }

    private fun submitPost() {
        val title = dialogAddPostView.findViewById<TextInputEditText>(R.id.addPostTitle).text.toString()
        val body = dialogAddPostView.findViewById<TextInputEditText>(R.id.addPostBody).text.toString()

        if (title.trim().isNotEmpty() && body.trim().isNotEmpty()) {
            val post = Post(
                body = body,
                id = postsLastId,
                title = title,
                userId = 1
            )
            mainActivityViewModel.savePost(post).invokeOnCompletion {
                Toast.makeText(this, "Saved", Toast.LENGTH_LONG).show()
                dialog.cancel()
                scrollToBottom()
            }
        } else {
            Toast.makeText(this, "Please fill in the form correctly", Toast.LENGTH_LONG).show()
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

    private fun displayPosts() {

        mainActivityViewModel.posts.observe(this) {
            val posts = it
            postsLastId = posts.size + 1
            val adapter = BlogPostsAdapter(posts)
            rvPosts.adapter = adapter

            rvPosts.layoutManager = linearLayoutManager
            loader("hide")
        }

    }
}