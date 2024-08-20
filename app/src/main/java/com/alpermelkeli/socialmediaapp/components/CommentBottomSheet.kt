package com.alpermelkeli.socialmediaapp.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.alpermelkeli.socialmediaapp.SocialMediaApplication
import com.alpermelkeli.socialmediaapp.model.Comment
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CommentBottomSheet(postId:String,comments:List<Comment>, sheetState: SheetState, onDismissRequest:()->Unit) {
    val scope = rememberCoroutineScope()

    val context = LocalContext.current.applicationContext as SocialMediaApplication

    val commentViewModel = context.commentsViewModel

    val userViewModel = context.userViewModel

    val user by userViewModel.user.observeAsState()

    var commentText by remember{ mutableStateOf("") }

    val sendComment = {
        user?.let {
            val newComment = Comment(it.id,it.profilePhoto,it.username,commentText,System.currentTimeMillis())
            commentViewModel.sendComment(postId,newComment)
        }
    }

    ModalBottomSheet(onDismissRequest = { onDismissRequest() },
        sheetState = sheetState) {
        Column(
            Modifier
                .fillMaxHeight()
                .imePadding()) {
            LazyColumn(Modifier.fillMaxHeight(0.8f)) {
                item {
                    Box(
                        Modifier
                            .fillMaxWidth()
                            .height(30.dp),
                        contentAlignment = Alignment.Center) {
                        Text(text = "Comments",
                            color = MaterialTheme.colorScheme.secondary,
                            fontWeight = FontWeight.ExtraBold,
                            fontSize = 16.sp)
                    }
                }
                items(comments){
                    CommentItem(it)
                }
            }
            Column(Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                DefaultTextField(placeHolder = "Write a comment", value = commentText, visibility = true) {
                    scope.launch {sheetState.expand()}
                    commentText = it
                }
                Button(onClick = {sendComment()},
                    colors = ButtonDefaults.buttonColors().copy(containerColor = MaterialTheme.colorScheme.secondary)) {
                    Text(text = "Send comment")
                }
            }
        }

    }


}
