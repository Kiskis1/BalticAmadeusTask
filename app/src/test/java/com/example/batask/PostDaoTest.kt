package com.example.batask

import android.content.Context
import androidx.lifecycle.asLiveData
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.batask.db.AppDatabase
import com.example.batask.db.dao.PostDao
import com.example.batask.model.Post
import kotlinx.coroutines.flow.asFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flatMapConcat
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.TestCoroutineScope
import kotlinx.coroutines.test.runBlockingTest
import org.hamcrest.CoreMatchers.equalTo
import org.hamcrest.MatcherAssert.assertThat
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import java.io.IOException

@RunWith(AndroidJUnit4::class)
class PostDaoTest {
    private lateinit var postDao: PostDao
    private lateinit var db: AppDatabase

    @Before
    fun createDb() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        db = Room.inMemoryDatabaseBuilder(
            context, AppDatabase::class.java).allowMainThreadQueries().build()
        postDao = db.postDao()
    }

    @After
    @Throws(IOException::class)
    fun closeDb() {
        db.close()
    }

    @Test
    fun getAllPostsEqualsMockPosts() {
        val posts: List<Post> = listOf(
            Post(1, 1, "test", "test"),
            Post(2, 2, "test2", "test2"))
        runBlocking {
            postDao.insertPosts(posts)
        }
        val postsFromDb =
            runBlocking { postDao.getAllPosts().first() }

        assertThat(postsFromDb, equalTo(posts))
    }

    @Test
    @Throws(Exception::class)
    fun getPostCountEqualsPostCount() {
        val posts: List<Post> = listOf(
            Post(1, 1, "test", "test"),
            Post(2, 2, "test2", "test2"))
        runBlocking {
            postDao.insertPosts(posts)
        }
        val count = runBlocking { postDao.getPostCount() }
        assertThat(count, equalTo(2))
    }

    @Test
    fun getPostById() {
        val posts: List<Post> = listOf(
            Post(1, 1, "test", "test"),
            Post(2, 2, "test2", "test2"))
        runBlocking {
            postDao.insertPosts(posts)
        }
        val post = runBlocking { postDao.getPost(1) }
        assertThat(post.body, equalTo(posts[0].body))
    }
}