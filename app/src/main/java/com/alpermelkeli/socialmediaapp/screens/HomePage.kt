package com.alpermelkeli.socialmediaapp.screens

import android.annotation.SuppressLint
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.alpermelkeli.socialmediaapp.components.HomePageTopBar
import com.alpermelkeli.socialmediaapp.components.Post
import com.alpermelkeli.socialmediaapp.components.StoriesRow
import com.alpermelkeli.socialmediaapp.model.Post
import com.alpermelkeli.socialmediaapp.model.User
import com.alpermelkeli.socialmediaapp.repository.AuthOperations
import com.alpermelkeli.socialmediaapp.ui.theme.SocialMediaAppTheme
import com.alpermelkeli.socialmediaapp.viewmodel.PostsViewModel
import com.alpermelkeli.socialmediaapp.viewmodel.UserViewModel

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun HomePage(postViewModel:PostsViewModel, userViewModel: UserViewModel){

    LaunchedEffect(Unit) {
        userViewModel.getUser(AuthOperations.getUser()?.uid.toString())
    }

    val user = userViewModel.user.observeAsState()

    LaunchedEffect(user.value) {
        val followingList = mutableListOf<String>()

        user.value?.following?.forEach {
            followingList.add(it.id)
        }

        postViewModel.getUserHomePagePosts(followingList)
    }

    val posts = postViewModel.homePagePosts.observeAsState(emptyList())

    val isDark = isSystemInDarkTheme()

    val storyRowScrollState = rememberLazyListState()

    val postColumnScrollState = rememberLazyListState()

    val exampleStories = listOf("user","user","user","user1","user2","user3")

    SocialMediaAppTheme {
        Scaffold(topBar = { HomePageTopBar(isDark = isDark, onClickCamera = {}, onClickMessages = {}) }) {
            LazyColumn(Modifier.fillMaxSize(),
                state = postColumnScrollState) {

                item {
                    Spacer(modifier = Modifier.height(90.dp))

                    StoriesRow(stories = exampleStories, scrollState = storyRowScrollState, onClickedStory = { println(it) })
                }

                items(posts.value){
                    println(posts.value)
                    Post(post = it)
                }
            }
        }
    }
}