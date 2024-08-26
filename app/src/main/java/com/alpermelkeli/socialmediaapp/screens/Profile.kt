package com.alpermelkeli.socialmediaapp.screens

import android.annotation.SuppressLint
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
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
import androidx.compose.material3.Icon
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
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberAsyncImagePainter
import com.alpermelkeli.socialmediaapp.R
import com.alpermelkeli.socialmediaapp.SocialMediaApplication
import com.alpermelkeli.socialmediaapp.components.DefaultOutlinedButton
import com.alpermelkeli.socialmediaapp.components.ProfilePostCard
import com.alpermelkeli.socialmediaapp.components.ProfileTopBar
import com.alpermelkeli.socialmediaapp.components.ShimmerEffectImage
import com.alpermelkeli.socialmediaapp.components.StoriesRow
import com.alpermelkeli.socialmediaapp.repository.AuthOperations

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun Profile(onClickedLogOut:()->Unit, onClickedPost:(postIndex:Int)->Unit){
    val context = LocalContext.current.applicationContext as SocialMediaApplication

    val userViewModel = context.userViewModel

    val user by userViewModel.user.observeAsState()


    val postViewModel = context.postsViewModel

    val userPosts by postViewModel.userPosts.observeAsState(emptyList())

    val postsCount by remember {
        derivedStateOf {
            userPosts.size.toString()
        }
    }
    val followersCount by remember {
        derivedStateOf {
            user?.followers?.size.toString()
        }
    }
    val followingsCount by remember {
        derivedStateOf {
            user?.following?.size.toString()
        }
    }

    LaunchedEffect(Unit) {
        postViewModel.getUserPosts(user?.id.toString())
    }

    val exampleStories = listOf("user2","user2","user2","user2","user2","user2","user2","user2","user2","user2","user2","user2","user2","user2","user2","user2",)

    val rowScrollState = rememberLazyListState()

    Scaffold(
        Modifier
            .fillMaxSize(),
        topBar = {ProfileTopBar(user?.username.toString(),onMenuClicked = {
            AuthOperations.logOut()
            onClickedLogOut()
        }
        )}) {
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
                            .size(90.dp), data = user?.profilePhoto.toString()
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
                    Spacer(modifier = Modifier.height(10.dp))

                    Column(
                        Modifier
                            .fillMaxWidth(),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(
                            text = user?.about.toString(),
                            textAlign = TextAlign.Start,
                            modifier = Modifier.fillMaxWidth()
                        )
                        Spacer(modifier = Modifier.height(20.dp))

                        DefaultOutlinedButton(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(35.dp), text = "Edit Profile"
                        ) {

                        }


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
            items(userPosts.chunked(3)) { rowItems ->
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
                            onClickedPost(userPosts.indexOf(it))
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


@Composable
fun IndicatorText(number:String,label:String){
    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally){
        Text(text = number,
            fontSize = 15.sp,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.secondary
        )

        Text(text = label,
            fontSize = 15.sp,
            fontWeight = FontWeight.SemiBold,
            color = MaterialTheme.colorScheme.secondary
        )
    }

}