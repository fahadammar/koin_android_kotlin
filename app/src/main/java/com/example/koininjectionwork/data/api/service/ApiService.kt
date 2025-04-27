package com.example.koininjectionwork.data.api.service

import com.example.koininjectionwork.data.api.apiData.Post
import retrofit2.http.GET

interface ApiService {
    companion object{
        const val BASE_URL = "https://jsonplaceholder.typicode.com/"
    }

    @GET("/post")
    suspend fun getPost():List<Post>
}