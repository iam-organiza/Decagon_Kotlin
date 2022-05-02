package com.example.implementationtwo.networking

import com.example.implementationtwo.ImageActivity
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


class AppConfig {

    var BASE_URL = "https://darot-image-upload-service.herokuapp.com"

    fun getRetrofit(): Retrofit? {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

}