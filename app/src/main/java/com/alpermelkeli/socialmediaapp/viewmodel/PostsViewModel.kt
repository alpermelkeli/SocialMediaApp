package com.alpermelkeli.socialmediaapp.viewmodel

import android.app.Application
import android.net.Uri
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.alpermelkeli.socialmediaapp.model.Comment
import com.alpermelkeli.socialmediaapp.model.Post
import com.alpermelkeli.socialmediaapp.repository.PostsRepository
import kotlinx.coroutines.launch

class PostsViewModel(application: Application, private val postsRepository: PostsRepository) : AndroidViewModel(application) {

    private val _homePagePosts : MutableLiveData<List<Post>> = MutableLiveData()

    val homePagePosts : LiveData<List<Post>> get() = _homePagePosts

    private val _userPosts : MutableLiveData<List<Post>> = MutableLiveData()

    val userPosts : LiveData<List<Post>> get() = _userPosts


    fun getUserHomePagePosts(followings:List<String>){
        viewModelScope.launch {
            postsRepository.getUserHomePagePosts(followings){
                _homePagePosts.postValue(it)
            }
        }
    }

    fun getUserPosts(id:String){
        viewModelScope.launch {
            postsRepository.getUserPosts(id){
                _userPosts.postValue(it)
            }
        }
    }


    fun uploadPost(post: Post, uri:Uri){
        viewModelScope.launch {
            postsRepository.uploadPhotoStorage(post.senderId,uri) { url->
                postsRepository.uploadUserPost(post.copy(images = listOf(url)))
            }
        }
    }

}