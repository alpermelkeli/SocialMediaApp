package com.alpermelkeli.socialmediaapp.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.alpermelkeli.socialmediaapp.model.Like
import com.alpermelkeli.socialmediaapp.repository.LikesRepository
import kotlinx.coroutines.launch


class LikesViewModel(application: Application, private val likesRepository: LikesRepository):
    AndroidViewModel(application){
    private val likes : MutableLiveData<List<Like>> = MutableLiveData()

    val like : LiveData<List<Like>> get() = likes

     fun getPostLikes(postId:String){
        viewModelScope.launch {
            likesRepository.getPostLikes(postId){
                likes.postValue(it)
            }
        }
    }


    fun updateLike(postId: String, like: Like){
        viewModelScope.launch {
            likesRepository.updateLikeData(postId, like)
            getPostLikes(postId)
        }
    }
}