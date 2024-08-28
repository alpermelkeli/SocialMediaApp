package com.alpermelkeli.socialmediaapp.screens

import android.annotation.SuppressLint
import android.net.Uri
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
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
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.ShapeDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberAsyncImagePainter
import com.alpermelkeli.socialmediaapp.SocialMediaApplication
import com.alpermelkeli.socialmediaapp.components.BackTopBar
import com.alpermelkeli.socialmediaapp.components.DefaultTextField
import com.alpermelkeli.socialmediaapp.components.ShimmerEffectImage
import com.alpermelkeli.socialmediaapp.model.Post
import com.alpermelkeli.socialmediaapp.model.Story
import com.alpermelkeli.socialmediaapp.ui.theme.Grey50
import java.util.UUID

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun SendStory(uri: String, onPostSent:()->Unit, onBackClicked:()->Unit){
    val context = LocalContext.current.applicationContext as SocialMediaApplication
    val userViewModel = context.userViewModel
    val user by userViewModel.user.observeAsState()
    val photoUri: Uri = Uri.parse(uri)
    val storiesViewModel = context.storiesViewModel

    val sendStory = {
        user?.let {
            val uuid = UUID.randomUUID()

            val newStory = Story(uuid.toString(),it.id,it.profilePhoto,it.username,"",System.currentTimeMillis())

            storiesViewModel.uploadStory(newStory, photoUri)

            onPostSent()
        }
    }

    Scaffold(
        Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)) {
        Box(modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight(0.8f)
            .clip(ShapeDefaults.Large),
            contentAlignment = Alignment.TopCenter){
            Image(painter = rememberAsyncImagePainter(model = photoUri), contentDescription = "",
                modifier = Modifier.fillMaxSize(),
                contentScale = ContentScale.Crop
            )
        }

        Column(
            Modifier
                .fillMaxSize()
                .padding(top = it.calculateTopPadding())
                .padding(horizontal = 10.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceBetween) {

            Row(Modifier.fillMaxWidth()) {
                IconButton(
                    onClick = { onBackClicked() },
                    modifier = Modifier
                        .size(40.dp)
                        .clip(CircleShape)
                        .background(Color.Black.copy(alpha = 0.3f))
                ) {
                    Icon(
                        imageVector = Icons.Default.ArrowBack,
                        contentDescription = "",
                        tint = MaterialTheme.colorScheme.secondary
                    )
                }
            }

            Row(Modifier.fillMaxWidth()
                .padding(bottom = it.calculateBottomPadding()),
                horizontalArrangement = Arrangement.Center) {
                Button(onClick = { sendStory() },
                    colors = ButtonDefaults.buttonColors().copy(containerColor = Grey50.copy(alpha = 0.5f)),
                    modifier = Modifier
                        .height(35.dp)
                        .fillMaxWidth(0.5f),
                    shape = ShapeDefaults.Large
                ) {

                    ShimmerEffectImage(modifier = Modifier
                        .size(20.dp)
                        .clip(CircleShape), data = user?.profilePhoto.toString())
                    Spacer(modifier = Modifier.width(5.dp))

                    Text(text = "Story",
                        fontSize = 12.sp,
                        color = MaterialTheme.colorScheme.secondary)
                }

            }

        }

    }



}
