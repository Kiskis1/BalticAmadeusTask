package com.example.batask

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.batask.db.AppDatabase
import com.example.batask.db.dao.UserDao
import com.example.batask.model.User
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import org.hamcrest.core.IsEqual.equalTo
import org.junit.After
import org.junit.Assert.assertFalse
import org.junit.Assert.assertNull
import org.junit.Assert.assertThat
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import java.io.IOException

@RunWith(AndroidJUnit4::class)
class UserDaoTest {
    private lateinit var userDao: UserDao
    private lateinit var db: AppDatabase
    private val users: List<User> = listOf(
        User(1, "testname", "username", "test", null, "123", "website", null),
        User(2, "nametest", "nameuser", "test2", null, "1234", "website2", null))

    @Before
    fun createDb() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        db = Room.inMemoryDatabaseBuilder(
            context, AppDatabase::class.java).build()
        userDao = db.userDao()
    }

    @After
    @Throws(IOException::class)
    fun closeDb() {
        db.close()
    }

    @Test
    fun test_read_user() {
        runBlocking {
            userDao.insertUser(users[0])
            userDao.insertUser(users[1])
        }
        val userFromDb =
            runBlocking { userDao.getUser(1).first() }

        assertThat(userFromDb, equalTo(users[0]))
    }

    @Test
    fun test_read_non_existant_user() {
        runBlocking {
            userDao.insertUser(users[0])
            userDao.insertUser(users[1])
        }
        val userFromDb =
            runBlocking { userDao.getUser(33).first() }

        assertNull(userFromDb)
    }

    @Test
    fun check_if_user_exists_true() {
        runBlocking {
            userDao.insertUser(users[0])
            userDao.insertUser(users[1])
        }
        val userExists = runBlocking { userDao.checkIfUserExists(users[1].id) }
        assertTrue(userExists)
    }

    @Test
    fun check_if_user_exists_false() {
        runBlocking {
            userDao.insertUser(users[0])
            userDao.insertUser(users[1])
        }
        val userExists = runBlocking { userDao.checkIfUserExists(4) }
        assertFalse(userExists)
    }
}