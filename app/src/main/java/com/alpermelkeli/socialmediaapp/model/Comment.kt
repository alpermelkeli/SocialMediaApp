package com.alpermelkeli.socialmediaapp.model

data class Comment(
    val senderId:String,
    val senderPhoto:String,
    val senderUsername:String,
    val content:String,
    val createdAt:Long
)
