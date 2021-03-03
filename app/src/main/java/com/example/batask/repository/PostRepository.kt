package com.example.batask.repository

import com.example.batask.db.dao.PostDao
import com.example.batask.model.Post
import com.example.batask.services.PostsService
import com.example.batask.services.RetrofitService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext
import retrofit2.Callback
import javax.inject.Inject

class PostRepository @Inject constructor(private val postDao: PostDao) {
    private val service = RetrofitService.buildService(PostsService::class.java)

    fun getAllPostsFromAPI(callback: Callback<List<Post>>) =
        service.getAllPosts().enqueue(callback)

    suspend fun insertPosts(body: List<Post>?) = postDao.insertPosts(body)


    fun getAllPostsFromDb(): Flow<List<Post>> = postDao.getAllPosts()

    suspend fun getPostCount(): Int = postDao.getPostCount()


    suspend fun getPost(postId: Long): Post {
        return withContext(Dispatchers.IO) {
            postDao.getPost(postId)
        }
    }
}