package com.alpermelkeli.socialmediaapp.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.alpermelkeli.socialmediaapp.model.Like
import com.alpermelkeli.socialmediaapp.repository.LikesRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class LikesViewModel(application: Application, private val likesRepository: LikesRepository) :
    AndroidViewModel(application) {

    private val _likesMap: MutableStateFlow<Map<String, List<Like>>> = MutableStateFlow(emptyMap())

    val likesMap: StateFlow<Map<String, List<Like>>> get() = _likesMap

    fun fetchPostLikes(postId: String) {
        viewModelScope.launch {
            likesRepository.getPostLikes(postId) { likes ->
                _likesMap.value = _likesMap.value.toMutableMap().apply {
                    this[postId] = likes
                }
            }
        }
    }

    fun updateLike(postId: String, like: Like) {
        viewModelScope.launch {
            likesRepository.sendLike(like)
            fetchPostLikes(postId) // Refresh the likes after updating
        }
    }
    fun removeLike(postId:String, likeId: String){
        viewModelScope.launch {
            likesRepository.removeLike(likeId)
            fetchPostLikes(postId)
        }
    }
}
