package com.example.batask.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.batask.model.User
import kotlinx.coroutines.flow.Flow

@Dao
interface UserDao {
    @Query("SELECT * FROM User WHERE User.id = :id")
    fun getUser(id: Long): Flow<User>

    @Query("SELECT EXISTS(SELECT * FROM User WHERE User.id = :id)")
    suspend fun checkIfUserExists(id: Long): Boolean

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertUser(user: User?)
}