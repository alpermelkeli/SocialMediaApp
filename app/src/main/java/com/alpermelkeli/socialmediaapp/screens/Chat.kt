package com.alpermelkeli.socialmediaapp.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.LayoutCoordinates
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.alpermelkeli.socialmediaapp.components.ChatBottomBar
import com.alpermelkeli.socialmediaapp.components.ChatTopBar
import com.alpermelkeli.socialmediaapp.components.ReceivedMessageItem
import com.alpermelkeli.socialmediaapp.components.SentMessageItem
import com.alpermelkeli.socialmediaapp.model.ChatMessage

@Preview
@Composable
fun Prev(){
    Chat {

    }
}


@Composable
fun Chat(onUpPressed:()-> Unit) {
    val chatMessage =ChatMessage(isReceived = false, content = "What is this...?", isEmojiOnly = false, senderPhoto = "", senderId = "")

    val chats = mutableListOf<ChatMessage>()

    for(i in 1..100){
        chats.add(chatMessage)
    }

    Column(Modifier.fillMaxSize()) {
        ChatTopBar(onUpPressed)

        ChatList(
            Modifier
                .fillMaxWidth()
                .weight(1f), chats)

        ChatBottomBar()
    }
}

@Composable
fun ChatList(modifier: Modifier, chats: List<ChatMessage>) {
    var listHeight by remember { mutableStateOf(0f) }
    LazyColumn(
        verticalArrangement = Arrangement.spacedBy(1.dp),
        modifier = modifier.onGloballyPositioned { coordinates: LayoutCoordinates ->
            listHeight = coordinates.size.height.toFloat()
        },
    ) {
        itemsIndexed(chats) { index, item ->
            if(item.isReceived) {
                ReceivedMessageItem(
                    chat = item.content,
                    isPrevReceived = if(index == 0) true else !chats[index-1].isReceived,
                    isNextReceived = if(index == chats.size - 1) true else !chats[index+1].isReceived,
                    isEmojiOnly = chats[index].isEmojiOnly,
                    isPrevEmojiOnly = if(index == 0) true else chats[index-1].isEmojiOnly,
                    isNextEmojiOnly = if(index == chats.size -1 ) false else chats[index+1].isEmojiOnly,
                )
            } else {
                SentMessageItem(
                    chat = item.content,
                    isPrevSent = if(index == 0) true else chats[index-1].isReceived,
                    isNextSent = if(index == chats.size - 1) true else chats[index+1].isReceived,
                    isEmojiOnly = chats[index].isEmojiOnly,
                    isPrevEmojiOnly = if(index == 0) true else chats[index-1].isEmojiOnly,
                    isNextEmojiOnly = if(index == chats.size -1 ) false else chats[index+1].isEmojiOnly,
                    listHeight = listHeight
                )
            }
        }
    }
}






