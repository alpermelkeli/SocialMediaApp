package com.alpermelkeli.socialmediaapp.components

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
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
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.filled.Send
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
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
import coil.annotation.ExperimentalCoilApi
import coil.compose.rememberAsyncImagePainter
import com.alpermelkeli.socialmediaapp.R
import com.alpermelkeli.socialmediaapp.model.Post
import com.alpermelkeli.socialmediaapp.SocialMediaApplication

@OptIn(ExperimentalFoundationApi::class, ExperimentalCoilApi::class)
@Composable
fun Post(post: Post, onClickedComment:()->Unit){

    val likeCount = post.likeCount

    val images = post.images

    val pagerState = rememberPagerState{images.size}

    Column(modifier = Modifier
        .fillMaxWidth()
        .height(500.dp),
        horizontalAlignment = Alignment.CenterHorizontally){

        Row(
            Modifier
                .fillMaxWidth()
                .height(60.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween){

            Row(modifier = Modifier
                .fillMaxHeight()
                .width(120.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically){

                Spacer(modifier = Modifier.width(10.dp))

                
                Image(painter = rememberAsyncImagePainter(model = post.senderPhoto), contentDescription = "photo",
                    modifier = Modifier
                        .size(40.dp)
                        .clip(CircleShape))

                Spacer(modifier = Modifier.width(8.dp))

                Column(horizontalAlignment = Alignment.Start,
                    verticalArrangement = Arrangement.SpaceBetween,
                    modifier = Modifier.height(25.dp)) {

                    Text(text = post.senderUsername,
                        fontSize = 10.sp,
                        color = MaterialTheme.colorScheme.secondary,
                        textAlign = TextAlign.Start,
                        fontWeight = FontWeight.Bold)

                }

            }

            Icon(imageVector = Icons.Default.MoreVert, contentDescription = "options",
                Modifier.padding(end = 10.dp),
                tint = MaterialTheme.colorScheme.secondary)

        }
        //Edit pager state by values.

        HorizontalPager(state = pagerState, modifier = Modifier
            .fillMaxWidth()
            .height(360.dp) ) {

            Box(modifier = Modifier
                .fillMaxWidth()
                .height(360.dp),
                contentAlignment = Alignment.Center) {

                Image(painter = rememberAsyncImagePainter(model = images[it]), contentDescription = "image",
                    modifier = Modifier.fillMaxSize(),
                    contentScale = ContentScale.Crop)

            }
        }

        Row(
            Modifier
                .fillMaxWidth()
                .height(40.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically) {

            Row(
                Modifier
                    .width(130.dp)
                    .fillMaxHeight()
                    .padding(start = 10.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically){
                Icon(imageVector = Icons.Default.FavoriteBorder, contentDescription = "like",
                    modifier = Modifier.size(35.dp),
                    tint = MaterialTheme.colorScheme.secondary)
                Icon(imageVector = ImageVector.vectorResource(id = R.drawable.comment_icon), contentDescription = "like",
                    modifier = Modifier.size(30.dp).clickable { onClickedComment() },
                    tint = MaterialTheme.colorScheme.secondary)
                Icon(imageVector = Icons.Default.Send, contentDescription = "like",
                    modifier = Modifier.size(30.dp),
                    tint = MaterialTheme.colorScheme.secondary)

            }

            Icon(imageVector = ImageVector.vectorResource(id = R.drawable.save_button),
                contentDescription = "save",
                modifier = Modifier
                    .size(35.dp)
                    .padding(end = 10.dp),
                tint = MaterialTheme.colorScheme.secondary)

        }
        Text(text = "$likeCount Likes",
            fontSize = 12.sp,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.secondary,
            textAlign = TextAlign.Start,
            modifier = Modifier
                .align(Alignment.Start)
                .padding(10.dp)
        )
    }

}