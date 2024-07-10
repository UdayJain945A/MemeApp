package com.example.memeapp

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create

object Retrofitinstance {
    val url="https://meme-api.com/"
    private val retrofit by lazy{
        Retrofit.Builder()
            .baseUrl(url)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

    }
    val apiInterfaces by lazy{
        retrofit.create(Memeinterface::class.java)
    }

}