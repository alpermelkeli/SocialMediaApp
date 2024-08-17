package com.alpermelkeli.socialmediaapp.screens

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
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
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.alpermelkeli.socialmediaapp.SocialMediaApplication
import com.alpermelkeli.socialmediaapp.components.Post

@Composable
fun UserPost(postIndex:Int, onBackClicked:()->Unit){
    val columnScrollState = rememberLazyListState()
    val context = LocalContext.current.applicationContext as SocialMediaApplication
    val postViewModel = context.postsViewModel
    val userPosts by postViewModel.userPosts.observeAsState(emptyList())

    LaunchedEffect(Unit) {
        columnScrollState.scrollToItem(postIndex)
    }

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
            Post(post = it)
        }
    }
}