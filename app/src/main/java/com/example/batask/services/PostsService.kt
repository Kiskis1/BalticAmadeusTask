package com.example.batask.services

import com.example.batask.model.Post
import com.example.batask.model.User
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface PostsService {
    @GET("posts")
    fun getAllPosts(): Call<List<Post>>

    @GET("posts/{id}")
    fun getPost(): Call<Post>

    @GET("users/{id}")
    fun getUser(@Path("id") id: Long): Call<User>
}