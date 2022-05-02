package com.example.implementationtwo.networking

import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.Header
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.PartMap

interface ApiConfig {

    @Multipart
    @POST("/api/v1/upload")
    fun upload(
    @Header("Content-Type") contentType: String,
    @PartMap map: HashMap<String, RequestBody>
    ): Call<ServerRes>

}