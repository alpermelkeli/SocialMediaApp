package com.alpermelkeli.socialmediaapp.model

import com.google.firebase.Timestamp

data class Conversation(
    val conversationId:String,
    val otherUserId:String,
    val profilePhoto:String,
    val username:String,
    val lastMessage:String,
    val lastUpdatedAt:Timestamp
)
