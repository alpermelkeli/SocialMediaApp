package com.alpermelkeli.socialmediaapp.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.alpermelkeli.socialmediaapp.model.LastMessage
import com.alpermelkeli.socialmediaapp.repository.MessageRepository

class MessageViewModel(application: Application, private val messageRepository: MessageRepository) : AndroidViewModel(application){

    private val _lastMessages : MutableLiveData<List<LastMessage>> = MutableLiveData()
    val lastMessages : LiveData<List<LastMessage>> get() = _lastMessages

    fun getLastMessages(){

    }





}