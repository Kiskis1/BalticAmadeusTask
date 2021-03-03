package com.example.batask.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.batask.db.dao.PostDao
import com.example.batask.db.dao.UserDao
import com.example.batask.model.Post
import com.example.batask.model.User

@Database(entities = [Post::class, User::class], version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {
    abstract fun postDao(): PostDao
    abstract fun userDao(): UserDao
}