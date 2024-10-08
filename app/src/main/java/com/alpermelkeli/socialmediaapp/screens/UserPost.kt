package com.alpermelkeli.socialmediaapp.screens

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.alpermelkeli.socialmediaapp.SocialMediaApplication
import com.alpermelkeli.socialmediaapp.components.CommentBottomSheet
import com.alpermelkeli.socialmediaapp.components.Post
import com.alpermelkeli.socialmediaapp.model.Like
import com.alpermelkeli.socialmediaapp.model.Post

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UserPost(postIndex:Int, isTarget:Boolean, onBackClicked:()->Unit,onClickedPostProfile:(userId:String)->Unit){

    val columnScrollState = rememberLazyListState()

    val context = LocalContext.current.applicationContext as SocialMediaApplication

    val postViewModel = context.postsViewModel

    val commentViewModel = context.commentsViewModel

    val userPosts by if(isTarget) postViewModel.targetUserPost.observeAsState(emptyList()) else postViewModel.userPosts.observeAsState(emptyList())

    val commentSheetState = rememberModalBottomSheetState()

    val comments by commentViewModel.comments.observeAsState(emptyList())

    var selectedPost by remember{ mutableStateOf("") }

    var isCommentSheetOpen by rememberSaveable {
        mutableStateOf(false)
    }


    val onClickedComment= {post:Post ->
        isCommentSheetOpen = true
        selectedPost = post.postId
        commentViewModel.getPostComments(post.postId)
    }

    LaunchedEffect(Unit) {
        columnScrollState.scrollToItem(postIndex)
    }
    Box(
        Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)) {
        Row(
            Modifier
                .fillMaxWidth()
                .height(60.dp)
                .padding(top = 15.dp)) {
            IconButton(onClick = { onBackClicked() }) {
                Icon(
                    imageVector = Icons.Default.ArrowBack, contentDescription = "back",
                    tint = MaterialTheme.colorScheme.secondary,
                    modifier = Modifier
                        .size(70.dp)
                        .padding(start = 10.dp)
                )
            }
        }
        LazyColumn(
            Modifier
                .fillMaxSize()
                .padding(top = 60.dp),
            state = columnScrollState) {

            items(userPosts){
                Post(post = it,
                    onClickedComment = { onClickedComment(it) },
                    onClickedProfile = {id -> onClickedPostProfile(id)}
                )

            }
        }
        if(isCommentSheetOpen){
            CommentBottomSheet(selectedPost,comments = comments, sheetState = commentSheetState, onDismissRequest = {isCommentSheetOpen = false})
        }
    }

}