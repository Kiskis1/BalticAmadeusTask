package com.example.batask.repository

import com.example.batask.db.dao.UserDao
import com.example.batask.model.User
import com.example.batask.services.PostsService
import com.example.batask.services.RetrofitService
import kotlinx.coroutines.flow.Flow
import retrofit2.Callback
import javax.inject.Inject

class UserRepository @Inject constructor(private val userDao: UserDao) {
    private val service = RetrofitService.buildService(PostsService::class.java)

    fun getUserFromAPI(userId: Long, callback: Callback<User>) =
        service.getUser(userId).enqueue(callback)

    fun getUserFromDb(userId: Long): Flow<User> =
        userDao.getUser(userId)

    suspend fun checkIfUserExists(userId: Long) =
        userDao.checkIfUserExists(userId)

    suspend fun insertUser(user: User?) =
        userDao.insertUser(user)
}