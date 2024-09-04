package com.alpermelkeli.socialmediaapp.screens

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.LayoutCoordinates
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.alpermelkeli.socialmediaapp.SocialMediaApplication
import com.alpermelkeli.socialmediaapp.components.ChatBottomBar
import com.alpermelkeli.socialmediaapp.components.ChatTopBar
import com.alpermelkeli.socialmediaapp.components.ReceivedMessageItem
import com.alpermelkeli.socialmediaapp.components.SentMessageItem
import com.alpermelkeli.socialmediaapp.model.ChatMessage
import com.alpermelkeli.socialmediaapp.model.MessageStatus
import com.alpermelkeli.socialmediaapp.model.MessageType
import com.google.firebase.Timestamp
import kotlinx.coroutines.launch


@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun Chat(conversationId: String, onUpPressed: () -> Unit) {
    val application = LocalContext.current.applicationContext as SocialMediaApplication

    val messageViewModel = application.messageViewModel
    val userViewModel = application.userViewModel

    val chatMessages by messageViewModel.conversationMessages.observeAsState(emptyList())
    val targetUser by userViewModel.targetUser.observeAsState()

    val scrollState = rememberLazyListState()

    LaunchedEffect(chatMessages.size) {
        if(chatMessages.size>0)scrollState.animateScrollToItem(chatMessages.size - 1)
    }

    Scaffold(
        topBar = { ChatTopBar(onUpPressed, targetUser) },
        bottomBar = {
            ChatBottomBar(
                onSentMessageClicked = { message ->
                    messageViewModel.sendMessage(conversationId, message)
                },
                50.dp
            )
        }
    ) { innerPadding ->
        ChatList(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding),
            chats = chatMessages,
            scrollState = scrollState
        )
    }
}

@Composable
fun ChatList(modifier: Modifier, chats: List<ChatMessage>, scrollState: LazyListState) {
    LazyColumn(
        state = scrollState,
        verticalArrangement = Arrangement.spacedBy(1.dp),
        modifier = modifier
    ) {
        itemsIndexed(chats) { index, item ->
            if (item.isReceived) {
                ReceivedMessageItem(
                    chat = item.content,
                    isPrevReceived = index == 0 || !chats[index - 1].isReceived,
                    isNextReceived = index == chats.size - 1 || !chats[index + 1].isReceived,
                    isEmojiOnly = item.isEmojiOnly,
                    isPrevEmojiOnly = index == 0 || chats[index - 1].isEmojiOnly,
                    isNextEmojiOnly = index == chats.size - 1 || chats[index + 1].isEmojiOnly
                )
            } else {
                SentMessageItem(
                    chat = item.content,
                    isPrevSent = index == 0 || chats[index - 1].isReceived,
                    isNextSent = index == chats.size - 1 || chats[index + 1].isReceived,
                    isEmojiOnly = item.isEmojiOnly,
                    isPrevEmojiOnly = index == 0 || chats[index - 1].isEmojiOnly,
                    isNextEmojiOnly = index == chats.size - 1 || chats[index + 1].isEmojiOnly
                )
            }
        }
    }
}





