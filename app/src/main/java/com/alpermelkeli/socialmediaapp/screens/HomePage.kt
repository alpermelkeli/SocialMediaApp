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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
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
import com.alpermelkeli.socialmediaapp.viewmodel.UserViewModel

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun HomePage(){


    val isDark = isSystemInDarkTheme()

    val storyRowScrollState = rememberLazyListState()

    val postColumnScrollState = rememberLazyListState()

    val exampleStories = listOf("user","user","user","user1","user2","user3")

    val exampleUser = User("1", "alpermelkeli", "https://avatars.githubusercontent.com/u/108495629?s=96&v=4", emptyList(), emptyList(), listOf(), "sa")

    val exampleUser2 = User("2", "mehmet","https://avatars.githubusercontent.com/u/108495629?s=96&v=4", listOf(exampleUser), listOf(exampleUser), listOf(), "sa")

    val examplePost = Post("1", exampleUser , emptyList(), emptyList(), 5, listOf("https://avatars.githubusercontent.com/u/108495629?s=96&v=4","https://avatars.githubusercontent.com/u/108495629?s=96&v=4"))

    val examplePost2 = Post("2", exampleUser , emptyList(), emptyList(), 5, listOf("https://avatars.githubusercontent.com/u/108495629?s=96&v=4","https://avatars.githubusercontent.com/u/108495629?s=96&v=4"))

    exampleUser.posts = listOf(examplePost,examplePost2)

    val homePagePosts = mutableListOf<Post>()

    exampleUser2.following.forEach { it.posts.forEach { homePagePosts.add(it) }  }


    SocialMediaAppTheme {
        Scaffold(topBar = { HomePageTopBar(isDark = isDark, onClickCamera = {}, onClickMessages = {}) }) {
            LazyColumn(Modifier.fillMaxSize(),
                state = postColumnScrollState) {

                item {
                    Spacer(modifier = Modifier.height(90.dp))

                    StoriesRow(stories = exampleStories, scrollState = storyRowScrollState, onClickedStory = { println(it) })
                }
                
                items(homePagePosts){
                    Post(post = it)
                }
            }
        }
    }
}