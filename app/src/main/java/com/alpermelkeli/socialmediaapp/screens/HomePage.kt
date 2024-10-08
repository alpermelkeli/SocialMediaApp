package com.alpermelkeli.socialmediaapp.screens

import android.Manifest
import android.annotation.SuppressLint
import android.util.Log
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.alpermelkeli.socialmediaapp.SocialMediaApplication
import com.alpermelkeli.socialmediaapp.components.CommentBottomSheet
import com.alpermelkeli.socialmediaapp.components.HomePageTopBar
import com.alpermelkeli.socialmediaapp.components.Post
import com.alpermelkeli.socialmediaapp.components.PullToRefreshLazyColumn
import com.alpermelkeli.socialmediaapp.components.StoriesRow
import com.alpermelkeli.socialmediaapp.model.Post
import com.alpermelkeli.socialmediaapp.repository.AuthOperations
import com.alpermelkeli.socialmediaapp.ui.theme.SocialMediaAppTheme
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun HomePage(onCameraClicked:()->Unit, onClickedPostProfile:(userId:String)->Unit, onClickedOwnPostProfile:()->Unit, onClickedMessages:()->Unit,onClickedStory:(String)->Unit) {

    val scope = rememberCoroutineScope()

    val isDark = isSystemInDarkTheme()

    val storyRowScrollState = rememberLazyListState()

    val postColumnScrollState = rememberLazyListState()

    val context = LocalContext.current.applicationContext as SocialMediaApplication

    val storiesViewModel = context.storiesViewModel

    val permissionViewModel = context.permissionViewModel

    val storeData = context.storeData

    val userViewModel = context.userViewModel

    val user by userViewModel.user.observeAsState()

    user?.let {
        scope.launch {
            storeData.saveUsername(it.username)
            storeData.saveProfilePhoto(it.profilePhoto)
        }
    }

    val postViewModel = context.postsViewModel

    val homePagePosts by postViewModel.homePagePosts.observeAsState(emptyList())

    val commentViewModel = context.commentsViewModel

    val comments by commentViewModel.comments.observeAsState(emptyList())

    var selectedPost by remember{ mutableStateOf("") }

    val stories by storiesViewModel.homePageStories.observeAsState(emptyList())

    LaunchedEffect(Unit) {
        userViewModel.getUser(AuthOperations.getUser()?.uid.toString())
    }


    LaunchedEffect(user) {
        user?.following?.let { postViewModel.getUserHomePagePosts(it)
            storiesViewModel.getHomePageStories(it)
        }
    }
    var isCommentSheetOpen by rememberSaveable {
        mutableStateOf(false)
    }


    val commentSheetState = rememberModalBottomSheetState()


    val onClickedComment = {post: Post ->
        isCommentSheetOpen = true
        scope.launch {
            commentSheetState.expand()
        }
        selectedPost = post.postId
        commentViewModel.getPostComments(post.postId)
    }

    var isRefreshing by remember {
        mutableStateOf(false)
    }

    val onRefresh = {
        scope.launch {
            isRefreshing = true
            delay(500)
            isRefreshing = false
            user?.following?.let {
                postViewModel.getUserHomePagePosts(it)
                storiesViewModel.getHomePageStories(it)
            }
        }
    }







    val permissionLauncher = rememberLauncherForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) {
        if (it) {
                onCameraClicked()
        } else {
            Toast.makeText(context, "Permission Denied", Toast.LENGTH_SHORT).show()
        }
    }

    SocialMediaAppTheme {
        Scaffold(topBar = {
            HomePageTopBar(isDark = isDark, onClickCamera = {
                permissionViewModel.checkCameraPermission(context, onGranted = {onCameraClicked()}, onUnGranted = {permissionLauncher.launch(Manifest.permission.CAMERA)})
            },
                onClickMessages = {
                    onClickedMessages()
                })
        }) {

            PullToRefreshLazyColumn(

                lazyListState = postColumnScrollState,
                modifier = Modifier
                    .fillMaxSize()
                    .padding(top = it.calculateTopPadding(),
                        bottom = it.calculateBottomPadding()),

                items = homePagePosts,

                content = {Post(post = it,
                    onClickedComment = { onClickedComment(it) },
                    onClickedProfile = {id ->
                        if(id == user?.id){
                            onClickedOwnPostProfile()
                        }
                        else{
                            onClickedPostProfile(id)
                        }
                    }
                )},

                staticContent = {StoriesRow(
                    true,
                    size = 100.dp,
                    profilePhoto = user?.profilePhoto.toString(),
                    stories = stories,
                    scrollState = storyRowScrollState,
                    onClickedAddCollection = {},
                    onClickedStory = {
                        onClickedStory(it)
                    })}
                ,
                isRefreshing = isRefreshing,

                onRefresh = { onRefresh() }
            )


            if(isCommentSheetOpen){
                CommentBottomSheet(selectedPost,comments = comments, sheetState = commentSheetState, onDismissRequest = {isCommentSheetOpen = false})
            }

        }
    }



}
