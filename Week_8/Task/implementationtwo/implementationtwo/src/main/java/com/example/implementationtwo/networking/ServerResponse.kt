package com.example.implementationtwo.networking

import com.google.gson.annotations.SerializedName

class ServerResponse {

    // variable name should be same as in the json response from php
    @SerializedName("success")
    var success: Boolean = false

    @SerializedName("message")
    var message: String? = null

    @JvmName("getMessage1")
    fun getMessage(): String? {
        return message
    }

    @JvmName("getSuccess1")
    fun getSuccess(): Boolean {
        return success
    }

}