package com.alpermelkeli.socialmediaapp.components

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.SheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.platform.LocalContext
import com.alpermelkeli.socialmediaapp.SocialMediaApplication

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun StartConversationBottomSheet(modalBottomSheetState: SheetState,onDismiss:()->Unit, redirectConversation:(conversationId:String, targetUserId:String)->Unit){
    val application = LocalContext.current.applicationContext as SocialMediaApplication

    val userViewModel = application.userViewModel

    val messageViewModel = application.messageViewModel

    val users by userViewModel.searchResults.observeAsState(emptyList())

    val conversations by messageViewModel.conversations.observeAsState(emptyList())

    var searchText by remember{ mutableStateOf("") }

    val onClickedStartConversation = { id:String ->
        val isThereConversation = conversations.filter { it.otherUserId == id }

        if(isThereConversation.size>0) redirectConversation(isThereConversation[0].conversationId,id)

        else {messageViewModel.startConversation(id){
            redirectConversation(it,id)
        }}
    }


    LaunchedEffect(searchText) {
        userViewModel.searchUsers(searchText)
    }

    ModalBottomSheet(onDismissRequest = {onDismiss()}, sheetState = modalBottomSheetState) {
        LazyColumn(horizontalAlignment = Alignment.CenterHorizontally) {
            item {
                DefaultSearchBar(searchText = searchText) {
                    searchText = it
                }
            }
            items(users){
                UserItem(profilePhoto = it.profilePhoto, userName = it.username){
                    onClickedStartConversation(it.id)
                }
            }

        }
    }


}