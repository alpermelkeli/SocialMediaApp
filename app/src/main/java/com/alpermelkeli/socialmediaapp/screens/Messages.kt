package com.alpermelkeli.socialmediaapp.screens

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.alpermelkeli.socialmediaapp.SocialMediaApplication
import com.alpermelkeli.socialmediaapp.components.DefaultSearchBar
import com.alpermelkeli.socialmediaapp.components.MessageTopBar
import com.alpermelkeli.socialmediaapp.components.LastMessageItem
import com.alpermelkeli.socialmediaapp.components.StartConversationBottomSheet

@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun Messages(onClickBack:()->Unit,onClickedConversation:(String)->Unit){

    val context = LocalContext.current.applicationContext as SocialMediaApplication

    val messageViewModel = context.messageViewModel

    val userViewModel = context.userViewModel

    val conversations by messageViewModel.conversations.observeAsState(emptyList())

    val modalSheetState = rememberModalBottomSheetState()

    var isBottomSheetOpen by rememberSaveable {
        mutableStateOf(false)
    }

    var searchText by remember{ mutableStateOf("") }

    LaunchedEffect(Unit) {
        messageViewModel.getConversations()
    }

    val redirectConversation = { targetUserId:String, conversationId:String ->
        messageViewModel.getConversationMessages(conversationId)
        userViewModel.getTargetUser(targetUserId)
        onClickedConversation(conversationId)
        isBottomSheetOpen = false

    }

    Scaffold(topBar = {MessageTopBar(onClickBack = {onClickBack()}, onClickAddMessage = {isBottomSheetOpen=true})}) {

        LazyColumn(
            Modifier
                .fillMaxSize()
                .padding(8.dp)
                .padding(top = it.calculateTopPadding(), bottom = it.calculateBottomPadding()),
            horizontalAlignment = Alignment.CenterHorizontally
            ){

            item {
                DefaultSearchBar(searchText = searchText) {
                    searchText = it
                }
                Spacer(modifier = Modifier.height(20.dp))


            }

            items(conversations) { conversation ->
                LastMessageItem(conversation = conversation, onConversationClicked = { targetUserId, conversationId ->
                    redirectConversation(targetUserId,conversationId)
                })
            }

        }
        if(isBottomSheetOpen){
            StartConversationBottomSheet(modalSheetState, onDismiss = {isBottomSheetOpen=false}){ conversationId,targetUserId ->
                redirectConversation(targetUserId,conversationId)
            }
        }

    }
}


