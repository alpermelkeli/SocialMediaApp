package com.alpermelkeli.socialmediaapp.model

data class Post(
    val id:String,
    val likeCount:Int,
    val images:List<String>,
    val senderPhoto:String,
    val senderUsername:String
)
