package com.example.week9task.adapters

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat.startActivity
import androidx.recyclerview.widget.RecyclerView
import com.example.week9task.PostActivity
import com.example.week9task.database.entities.Comment
import com.example.week9task.databinding.PostCommentsViewBinding
import com.example.week9task.databinding.PostViewBinding
import com.example.week9task.models.Comments
import com.example.week9task.models.Posts

class BlogPostCommentsAdapter(private val comments: List<Comment>): RecyclerView.Adapter<BlogPostCommentsAdapter.PostCommentsViewHolder>() {
    inner class PostCommentsViewHolder(binding: PostCommentsViewBinding): RecyclerView.ViewHolder(binding.root) {
        val name = binding.postCommentName
        val email = binding.postCommentEmail
        val body = binding.postCommentBody
        val card = binding.postCommentViewCard
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PostCommentsViewHolder {
        return PostCommentsViewHolder(PostCommentsViewBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holderComments: PostCommentsViewHolder, position: Int) {
        val comments = comments[position]

        holderComments.name.text = comments.name
        holderComments.email.text = comments.email
        holderComments.body.text = comments.body

//        holderComments.title.text = postItem.title
//        holderComments.body.text = postItem.body

//        holderComments.card.setOnClickListener {
//            val intent = Intent(holderComments.itemView.context, PostActivity::class.java)
//            intent.putExtra("postItem", postItem)
//
//            startActivity(holderComments.itemView.context, intent, null)
//        }
    }

    override fun getItemCount(): Int = comments.size
}