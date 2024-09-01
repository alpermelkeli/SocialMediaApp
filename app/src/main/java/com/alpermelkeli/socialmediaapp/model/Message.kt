package com.alpermelkeli.socialmediaapp.model

data class Message(
    val messageId: String,
    val senderId:String,
    val content:String,
    val createdAt:String
)
