package com.example.batask.ui.singlepost

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.example.batask.SingleLiveEvent
import com.example.batask.model.Post
import com.example.batask.model.User
import com.example.batask.repository.PostRepository
import com.example.batask.repository.UserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import javax.inject.Inject

@HiltViewModel
class SinglePostViewModel @Inject constructor(
    private val userRepository: UserRepository,
    private val postRepository: PostRepository,
) : ViewModel() {

    val error = SingleLiveEvent<Boolean>()

    private val postData = MutableLiveData<Pair<LiveData<Post>, LiveData<User>>>()

    private fun getPost(postId: Long): LiveData<Post> {
        val post = MutableLiveData<Post>()
        viewModelScope.launch {
            post.postValue(postRepository.getPost(postId))
        }
        return post
    }

    private fun getUserFromApi(userId: Long){
        userRepository.getUserFromAPI(userId, object : Callback<User> {
            override fun onResponse(call: Call<User>, response: Response<User>) {
                if (response.isSuccessful) {
                    viewModelScope.launch {
                        userRepository.insertUser(response.body())
                    }
                }
            }

            override fun onFailure(call: Call<User>, t: Throwable) {
                error.postValue(true)
            }
        })
    }

    //could be better
    private fun getUser(userId: Long): LiveData<User> {
        viewModelScope.launch {
            if(!userRepository.checkIfUserExists(userId)){
                getUserFromApi(userId)
            }
        }
        return userRepository.getUserFromDb(userId).asLiveData()
    }

    fun getPostData(post: Post): MutableLiveData<Pair<LiveData<Post>, LiveData<User>>> {
        postData.postValue(Pair(getPost(post.id),getUser(post.userId)))
        return postData
    }
}