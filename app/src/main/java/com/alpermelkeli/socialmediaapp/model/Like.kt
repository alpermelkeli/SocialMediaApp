package com.alpermelkeli.socialmediaapp.model


data class Like(
    val postId:String,
    val userId:String,
    val createdAt:Long
)

