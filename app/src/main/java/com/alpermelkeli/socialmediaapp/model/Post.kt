package com.alpermelkeli.socialmediaapp.model

data class Post(
    val postId:String,
    val senderId:String,
    val likeCount:Int,
    val images:List<String>,
    val senderPhoto:String,
    val senderUsername:String,
    val timestamp:Long
)
