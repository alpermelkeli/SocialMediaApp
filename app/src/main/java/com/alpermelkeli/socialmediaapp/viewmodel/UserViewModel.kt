package com.alpermelkeli.socialmediaapp.viewmodel
import android.app.Application
import android.net.Uri
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.alpermelkeli.socialmediaapp.model.User
import com.alpermelkeli.socialmediaapp.repository.UserRepository
import kotlinx.coroutines.launch

class UserViewModel(application: Application, private val userRepository: UserRepository): AndroidViewModel(application) {


    private val _user : MutableLiveData<User?> = MutableLiveData()

    val user : MutableLiveData<User?> get() = _user

    private val _targetUser : MutableLiveData<User?> = MutableLiveData()

    val targetUser : MutableLiveData<User?> get() = _targetUser

    private val _searchResults: MutableLiveData<List<User>> = MutableLiveData()

    val searchResults: LiveData<List<User>> get() = _searchResults

    fun getUser(id: String) {
        viewModelScope.launch {
            val user = userRepository.getUserDocument(id)
            _user.postValue(user)
        }
    }

    fun getTargetUser(id: String) {
        viewModelScope.launch {
            val targetUser = userRepository.getUserDocument(id)
            _targetUser.postValue(targetUser)
        }
    }
    fun searchUsers(username: String) {
        viewModelScope.launch {
            userRepository.searchUsersByUsername(username) { users ->
                _searchResults.postValue(users)
            }
        }
    }
    fun followUser(userId:String, followingId:String){
        viewModelScope.launch {
            userRepository.followUser(userId,followingId){
                getUser(userId)
                getTargetUser(followingId)
            }
        }
    }
    fun unfollowUser(userId:String, followingId:String){
        viewModelScope.launch {
            userRepository.unfollowUser(userId,followingId){
                getUser(userId)
                getTargetUser(followingId)
            }
        }
    }
    fun updateProfilePhoto(userId: String, uri: Uri){
        viewModelScope.launch {
            userRepository.uploadProfilePhotoStorage(userId,uri){
                userRepository.uploadProfilePhoto(userId,it)
                getUser(userId)
            }
        }

    }



}