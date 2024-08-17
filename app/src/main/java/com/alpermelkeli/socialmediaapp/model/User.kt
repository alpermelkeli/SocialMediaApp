package com.alpermelkeli.socialmediaapp.model

data class User(
    val id:String,
    val username:String,
    val profilePhoto:String,
    val followers:List<String>,
    val following:List<String>,
    val about:String
)
