package com.alpermelkeli.socialmediaapp.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.alpermelkeli.socialmediaapp.model.Comment
import com.alpermelkeli.socialmediaapp.repository.CommentsRepository
import kotlinx.coroutines.launch

class CommentsViewModel(application: Application, private val commentsRepository: CommentsRepository):AndroidViewModel(application) {

    private val _comments : MutableLiveData<List<Comment>> = MutableLiveData()

    val comments : LiveData<List<Comment>> get() = _comments


    fun getPostComments(postId:String){
        viewModelScope.launch {
            commentsRepository.getPostComments(postId){
                _comments.postValue(it)
            }
        }
    }
    fun sendComment(postId: String, comment: Comment){
        viewModelScope.launch {
            commentsRepository.sendComment(postId,comment)
            getPostComments(postId)
        }
    }
}