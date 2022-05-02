package com.example.implementationtwo.networking

data class ServerRes(
    val message: String,
    val payload: Payload,
    val status: Int
)