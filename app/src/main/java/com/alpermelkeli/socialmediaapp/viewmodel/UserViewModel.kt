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


    fun getUser(id:String){
        viewModelScope.launch {
            userRepository.getUserDocument(id){
             _user.postValue(it)
            }
        }
    }



}