package com.alpermelkeli.socialmediaapp.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.alpermelkeli.socialmediaapp.model.Post
import com.alpermelkeli.socialmediaapp.repository.PostsRepository
import com.alpermelkeli.socialmediaapp.repository.UserRepository
import kotlinx.coroutines.launch

class PostsViewModel(application: Application, private val postsRepository: PostsRepository) : AndroidViewModel(application) {

    private val _homePagePosts : MutableLiveData<List<Post>> = MutableLiveData()

    val homePagePosts : LiveData<List<Post>> get() = _homePagePosts



    fun getUserHomePagePosts(followings:List<String>){
        viewModelScope.launch {
            postsRepository.getUserHomePagePosts(followings){
                _homePagePosts.postValue(it)
            }
        }
    }






}