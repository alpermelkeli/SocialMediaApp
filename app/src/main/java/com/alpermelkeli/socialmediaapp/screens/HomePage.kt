package com.alpermelkeli.socialmediaapp.screens

import android.Manifest
import android.annotation.SuppressLint
import android.content.Context
import android.content.pm.PackageManager
import android.icu.text.SimpleDateFormat
import android.net.Uri
import android.util.Log
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat
import androidx.core.content.FileProvider
import coil.compose.rememberImagePainter
import com.alpermelkeli.socialmediaapp.SocialMediaApplication
import com.alpermelkeli.socialmediaapp.components.HomePageTopBar
import com.alpermelkeli.socialmediaapp.components.Post
import com.alpermelkeli.socialmediaapp.components.StoriesRow
import com.alpermelkeli.socialmediaapp.fetchingPhoto.FetchPhotoUsingCam
import com.alpermelkeli.socialmediaapp.repository.AuthOperations
import com.alpermelkeli.socialmediaapp.ui.theme.SocialMediaAppTheme
import java.io.File
import java.util.Date
import java.util.Objects

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun HomePage() {
    val context = LocalContext.current.applicationContext as SocialMediaApplication

    val userViewModel = context.userViewModel
    val user by userViewModel.user.observeAsState()

    val postViewModel = context.postsViewModel
    val homePagePosts by postViewModel.homePagePosts.observeAsState(emptyList())

    LaunchedEffect(Unit) {
        userViewModel.getUser(AuthOperations.getUser()?.uid.toString())
    }


    LaunchedEffect(user) {
        user?.following?.let { postViewModel.getUserHomePagePosts(it) }
    }


    val isDark = isSystemInDarkTheme()

    val storyRowScrollState = rememberLazyListState()

    val postColumnScrollState = rememberLazyListState()

    val exampleStories = listOf("user", "user", "user", "user1", "user2", "user3")



    var showCamera by remember { mutableStateOf(false) }
    val permissionLauncher = rememberLauncherForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) {
        if (it) {
            Toast.makeText(context, "Permission Granted", Toast.LENGTH_SHORT).show()


        } else {
            Toast.makeText(context, "Permission Denied", Toast.LENGTH_SHORT).show()
        }
    }

    SocialMediaAppTheme {
        Scaffold(topBar = {
            HomePageTopBar(isDark = isDark, onClickCamera = {
                val permissionCheckResult =
                    ContextCompat.checkSelfPermission(context, Manifest.permission.CAMERA)
                if (permissionCheckResult == PackageManager.PERMISSION_GRANTED) {
                    Toast.makeText(context, "Permission granted", Toast.LENGTH_SHORT).show()
                    showCamera = true
                } else {
                    // Request a permission
                    permissionLauncher.launch(Manifest.permission.CAMERA)
                }
            },
                onClickMessages = {
                    Log.d("Direct Message", "Direct Message has been invoked")
                })
        }) {
            LazyColumn(
                Modifier.fillMaxSize(),
                state = postColumnScrollState
            ) {

                item {
                    Spacer(modifier = Modifier.height(90.dp))

                    StoriesRow(
                        size = 100.dp,
                        stories = exampleStories,
                        scrollState = storyRowScrollState,
                        onClickedStory = { println(it) })
                }

                items(homePagePosts) {
                    Post(post = it)
                }
            }
        }
    }

    if (showCamera){
        FetchPhotoUsingCam(context)
    }

}
