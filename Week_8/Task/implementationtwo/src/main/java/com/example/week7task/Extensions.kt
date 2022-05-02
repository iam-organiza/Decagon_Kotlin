package com.example.week7task

import android.content.Context
import android.content.Intent
import android.view.View
import androidx.core.content.ContextCompat


fun View.hide() {
    this.visibility = View.GONE
}


fun View.show() {
    this.visibility = View.VISIBLE
}


inline fun <reified T> navigate(context: Context, destination: T){
    val intent = Intent(context, destination!!::class.java)
    ContextCompat.startActivity(context, intent, null)
}
