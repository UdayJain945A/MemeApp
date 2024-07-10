package com.example.memeapp


import retrofit2.Call
import retrofit2.http.GET

interface Memeinterface {
    @GET("gimme")
    fun getMeme(): Call<Mydata>
}

