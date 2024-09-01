package com.alpermelkeli.socialmediaapp.model

data class ChatMessage(
    val isReceived: Boolean,
    val content: String,
    val isEmojiOnly: Boolean,
    val senderId:String,
    val senderPhoto:String)
