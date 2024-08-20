package com.alpermelkeli.socialmediaapp.model

data class Comment(
    val senderId:String,
    val content:String,
    val createdAt:Long
)
