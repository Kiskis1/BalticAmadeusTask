package com.example.batask.ui.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.example.batask.SingleLiveEvent
import com.example.batask.model.Post
import com.example.batask.repository.PostRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val postRepository: PostRepository,
) : ViewModel() {

    val error = SingleLiveEvent<Boolean>()

    fun getPosts(): LiveData<List<Post>> {
        viewModelScope.launch {
            if (postRepository.getPostCount() == 0) {
                getAllPosts()
            }
        }
        return postRepository.getAllPostsFromDb().asLiveData()
    }

    fun getAllPosts() {
        postRepository.getAllPostsFromAPI(object : Callback<List<Post>> {
            override fun onResponse(call: Call<List<Post>>, response: Response<List<Post>>) {
                if (response.isSuccessful) {
                    viewModelScope.launch {
                        insertPosts(response.body())
                    }
                }
            }

            override fun onFailure(call: Call<List<Post>>, t: Throwable) {
                error.postValue(true)
            }
        })
    }

    private suspend fun insertPosts(body: List<Post>?) {
        postRepository.insertPosts(body)
    }
}