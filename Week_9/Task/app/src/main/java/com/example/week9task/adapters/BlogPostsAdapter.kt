package com.example.week9task.adapters

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat.startActivity
import androidx.recyclerview.widget.RecyclerView
import com.example.week9task.PostActivity
import com.example.week9task.database.entities.Post
import com.example.week9task.databinding.PostViewBinding
import com.example.week9task.models.Posts

class BlogPostsAdapter(private val posts: List<Post>): RecyclerView.Adapter<BlogPostsAdapter.PostViewHolder>() {
    inner class PostViewHolder(binding: PostViewBinding): RecyclerView.ViewHolder(binding.root) {
        val title = binding.postTitle
        val body = binding.postBody
        val card = binding.postViewCard
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PostViewHolder {
        return PostViewHolder(PostViewBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: PostViewHolder, position: Int) {
        val postItem = posts[position]

        holder.title.text = postItem.title
        holder.body.text = postItem.body

        holder.card.setOnClickListener {
            val intent = Intent(holder.itemView.context, PostActivity::class.java)
            intent.putExtra("postItem", postItem)

            startActivity(holder.itemView.context, intent, null)
        }
    }

    override fun getItemCount(): Int = posts.size
}