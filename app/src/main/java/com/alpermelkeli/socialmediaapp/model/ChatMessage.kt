package com.alpermelkeli.socialmediaapp.model

import com.google.firebase.Timestamp

data class ChatMessage(
    val isReceived: Boolean,
    val status: MessageStatus,
    val content: String,
    val isEmojiOnly: Boolean,
    val senderId:String,
    val type: MessageType,
    val timestamp: Timestamp
    )

enum class MessageType{
    TEXT, IMAGE, VOICE
}
enum class MessageStatus{
    SENT,READ
}