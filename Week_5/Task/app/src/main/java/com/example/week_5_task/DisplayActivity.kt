package com.example.week_5_task

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import org.w3c.dom.Text

class DisplayActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_display)

        findViewById<ImageView>(R.id.closeDetails).setOnClickListener {
            closeDetails()
        }
    }

    override fun onResume() {
        super.onResume()
        displayDetails()
    }

    private fun closeDetails() {
        val intent = Intent(this, MainActivity::class.java).also {
            startActivity(it)
        }
    }

    private fun displayDetails() {
        val name = intent.getStringExtra("name")
        val phone = intent.getStringExtra("phone")
        val email = intent.getStringExtra("email")
        val gender = intent.getStringExtra("gender")

        findViewById<TextView>(R.id.displayName).text = name
        findViewById<TextView>(R.id.displayEmail).text = email
        findViewById<TextView>(R.id.displayPhone).text = phone
        findViewById<TextView>(R.id.displayGender).text = gender
    }
}