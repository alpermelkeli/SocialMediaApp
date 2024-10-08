package com.alpermelkeli.socialmediaapp.screens

import android.annotation.SuppressLint
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter
import com.alpermelkeli.socialmediaapp.R
import com.alpermelkeli.socialmediaapp.SocialMediaApplication
import com.alpermelkeli.socialmediaapp.components.BackTopBar
import com.alpermelkeli.socialmediaapp.components.DefaultButton
import com.alpermelkeli.socialmediaapp.components.DefaultOutlinedButton
import com.alpermelkeli.socialmediaapp.components.ProfilePostCard
import com.alpermelkeli.socialmediaapp.components.ProfileTopBar
import com.alpermelkeli.socialmediaapp.components.ShimmerEffectImage
import com.alpermelkeli.socialmediaapp.components.StoriesRow
import com.alpermelkeli.socialmediaapp.repository.AuthOperations

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun TargetProfile(targetUserId:String, onClickedPost:(postIndex:Int)->Unit, onClickedBack:()->Unit){

    val context = LocalContext.current.applicationContext as SocialMediaApplication

    val userViewModel = context.userViewModel

    val user by userViewModel.user.observeAsState()

    val targetUser by userViewModel.targetUser.observeAsState()

    val postViewModel = context.postsViewModel

    val targetUserPosts by postViewModel.targetUserPost.observeAsState(emptyList())

    val postsCount by remember {
        derivedStateOf {
            targetUserPosts.size.toString()
        }
    }
    val followersCount by remember {
        derivedStateOf {
            targetUser?.followers?.size.toString()
        }
    }
    val followingsCount by remember {
        derivedStateOf {
            targetUser?.following?.size.toString()
        }
    }

    val isFollowing by remember {
        derivedStateOf {
            user?.following?.any { it == targetUserId }
        }
    }

    LaunchedEffect(Unit) {
        userViewModel.getTargetUser(targetUserId)
        postViewModel.getTargetUserPosts(targetUserId)
    }

    val exampleStories = listOf("user2","user2","user2","user2","user2","user2","user2","user2","user2","user2","user2","user2","user2","user2","user2","user2",)

    val rowScrollState = rememberLazyListState()

    Scaffold(Modifier.fillMaxSize(), topBar = { BackTopBar {
        onClickedBack()
    }}) {
        LazyColumn(Modifier.fillMaxHeight(),
            horizontalAlignment = Alignment.CenterHorizontally) {
            item {
                Column(
                    Modifier
                        .fillMaxWidth()
                        .padding(top = 90.dp)
                        .padding(horizontal = 10.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    Row(
                        Modifier
                            .fillMaxWidth()
                            .height(120.dp),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        ShimmerEffectImage(modifier = Modifier
                            .clip(CircleShape)
                            .size(90.dp), data = (targetUser?.profilePhoto.toString())
                        )

                        Row(
                            Modifier
                                .fillMaxHeight()
                                .width(250.dp)
                                .padding(end = 30.dp),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            IndicatorText(number = postsCount, label = "Posts")

                            IndicatorText(number = followersCount, label = "Followers")

                            IndicatorText(number = followingsCount, label = "Following")
                        }

                    }

                    Row(
                        Modifier
                            .fillMaxWidth()
                            .height(40.dp)) {
                        Spacer(modifier = Modifier.width(110.dp))
                        DefaultButton(modifier = Modifier
                            .width(250.dp)
                            .height(40.dp)
                            , text = if(isFollowing == true) "Unfollow" else "Follow", isEnabled = true) {
                            if(isFollowing == true){
                                user?.id?.let { userViewModel.unfollowUser(it, targetUserId) }
                            }
                            else{
                                user?.id?.let { userViewModel.followUser(it,targetUserId) }
                            }
                        }
                    }

                    Row(
                        Modifier
                            .fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically,
                    ) {
                        Text(
                            text = targetUser?.about.toString(),
                            textAlign = TextAlign.Start,
                            modifier = Modifier.width(200.dp),
                            color = MaterialTheme.colorScheme.secondary
                        )

                    }
                }//Bio side.
                Spacer(modifier = Modifier.height(15.dp))

                /*Row(
                    Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 10.dp)
                        .height(80.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center){

                    StoriesRow(false, profilePhoto = targetUser?.profilePhoto.toString(),size = 80.dp, stories = exampleStories, scrollState = rowScrollState, onClickedAddCollection = {}) {


                    }

                }*/
                Spacer(modifier = Modifier.height(20.dp))

                Spacer(modifier = Modifier
                    .height(1.dp)
                    .fillMaxWidth()
                    .background(MaterialTheme.colorScheme.secondary))

            }
            items(targetUserPosts.chunked(3)) { rowItems ->
                Row(
                    modifier = Modifier
                        .fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    rowItems.forEach { post ->
                        ProfilePostCard(post, Modifier
                            .weight(1f)
                            .padding(0.5.dp)
                            .height(120.dp)){
                            onClickedPost(targetUserPosts.indexOf(it))
                        }
                    }
                    if (rowItems.size < 3) {
                        repeat(3 - rowItems.size) {
                            Spacer(modifier = Modifier
                                .weight(1f)
                                .padding(4.dp))
                        }
                    }
                }
            }

        }
    }
}



