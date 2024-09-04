package com.alpermelkeli.socialmediaapp.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.alpermelkeli.socialmediaapp.model.ChatMessage
import com.alpermelkeli.socialmediaapp.model.Conversation
import com.alpermelkeli.socialmediaapp.repository.MessageRepository
import kotlinx.coroutines.launch

class MessageViewModel(application: Application, private val messageRepository: MessageRepository) : AndroidViewModel(application){

    private val _conversations : MutableLiveData<List<Conversation>> = MutableLiveData()
    val conversations : LiveData<List<Conversation>> get() = _conversations

    private val _conversationMessages : MutableLiveData<List<ChatMessage>> = MutableLiveData()
    val conversationMessages : LiveData<List<ChatMessage>> get() = _conversationMessages

    fun getConversations(){
        viewModelScope.launch {
            messageRepository.getConversations {
                _conversations.postValue(it)
            }
        }
    }

    fun getConversationMessages(conversationId:String){
        viewModelScope.launch {
            messageRepository.getMessages(conversationId){
                _conversationMessages.postValue(it)
            }
        }
    }

    fun sendMessage(conversationId: String,content:String){
        viewModelScope.launch {
            messageRepository.sendMessage(conversationId,content){
            }
        }
    }

    fun startConversation(otherUserId: String, onConversationCreated: (conversationId: String) -> Unit) {
        viewModelScope.launch {
            messageRepository.startConversation(otherUserId) { conversationId ->
                conversationId.let {
                    getConversations()
                    onConversationCreated(it)
                }
            }
        }
    }





}