package com.example.batask.modules

import android.content.Context
import androidx.room.Room
import com.example.batask.db.AppDatabase
import com.example.batask.db.dao.PostDao
import com.example.batask.db.dao.UserDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
class DatabaseModule {
    @Provides
    fun providePostDao(appDatabase: AppDatabase): PostDao {
        return appDatabase.postDao()
    }

    @Provides
    fun provideUserDao(appDatabase: AppDatabase): UserDao {
        return appDatabase.userDao()
    }

    @Provides
    @Singleton
    fun provideAppDatabase(@ApplicationContext appContext: Context): AppDatabase {
        return Room.databaseBuilder(
            appContext,
            AppDatabase::class.java,
            "ba_db"
        ).build()
    }

}