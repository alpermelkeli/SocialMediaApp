package com.alpermelkeli.socialmediaapp.model

data class User(
    val id:String,
    val username:String,
    val profilePhoto:String,
    val followers:List<User>,
    val following:List<User>,
    var posts:List<Post>,
    val about:String
)
