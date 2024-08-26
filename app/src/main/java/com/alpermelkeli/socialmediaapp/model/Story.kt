package com.alpermelkeli.socialmediaapp.model

data class Story(
    val storyId:String,
    val senderId:String,
    val profilePhoto:String,
    val username:String,
    val image:String,
    val timestamp:Long,
)
