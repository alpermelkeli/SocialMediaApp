package com.alpermelkeli.socialmediaapp.viewmodel
import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.alpermelkeli.socialmediaapp.model.User
import com.alpermelkeli.socialmediaapp.repository.UserRepository
import kotlinx.coroutines.launch

class UserViewModel(application: Application, private val userRepository: UserRepository): AndroidViewModel(application) {


    private val _user : MutableLiveData<User> = MutableLiveData()

    val user : LiveData<User> get() = _user

    private val _searchResults: MutableLiveData<List<User>> = MutableLiveData()

    val searchResults: LiveData<List<User>> get() = _searchResults

    fun getUser(id:String){
        viewModelScope.launch {
            userRepository.getUserDocument(id){
             _user.postValue(it)
            }
        }
    }
    fun searchUsers(username: String) {
        userRepository.searchUsersByUsername(username) { users ->
            _searchResults.postValue(users)
        }
    }



}