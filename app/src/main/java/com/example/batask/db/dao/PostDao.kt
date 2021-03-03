package com.example.batask.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.batask.model.Post
import kotlinx.coroutines.flow.Flow

@Dao
interface PostDao {
    @Query("SELECT * FROM Post")
    fun getAllPosts(): Flow<List<Post>>

    @Query("SELECT COUNT(*) FROM Post")
    suspend fun getPostCount(): Int

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertPosts(posts: List<Post>?)

    @Query("SELECT * FROM Post WHERE Post.id = :id")
    fun getPost(id: Long): Post
}