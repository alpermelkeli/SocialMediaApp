package com.alpermelkeli.socialmediaapp.model

data class Post(
    val id:String,
    val sender:User,
    val comments:List<String>,
    val likes:List<User>,
    val likeCount:Int,
    val images:List<String>
)
