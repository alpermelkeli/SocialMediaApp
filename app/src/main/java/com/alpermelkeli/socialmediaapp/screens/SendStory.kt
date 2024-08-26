package com.alpermelkeli.socialmediaapp.screens

import android.net.Uri
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter
import com.alpermelkeli.socialmediaapp.SocialMediaApplication
import com.alpermelkeli.socialmediaapp.components.DefaultTextField
import com.alpermelkeli.socialmediaapp.model.Post
import java.util.UUID

@Composable
fun SendStory(uri: String, onPostSent:()->Unit){
    val context = LocalContext.current.applicationContext as SocialMediaApplication
    val postViewModel = context.postsViewModel
    val userViewModel = context.userViewModel
    val user by userViewModel.user.observeAsState()
    var descriptionText by remember{ mutableStateOf("") }
    val photoUri: Uri = Uri.parse(uri)

    val sendPost = {
        user?.let {
            val uuid = UUID.randomUUID()
            val newPost = Post(uuid.toString(),user!!.id, emptyList(),user!!.profilePhoto,user!!.username, System.currentTimeMillis())
            onPostSent()
        }
    }


    Box(modifier = Modifier
        .fillMaxSize()
        .background(MaterialTheme.colorScheme.background)){

        Image(painter = rememberAsyncImagePainter(model = photoUri), contentDescription = "",
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight(0.8f))

        Column(
            Modifier
                .fillMaxSize()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally) {


            DefaultTextField(placeHolder = "Description", value = descriptionText, visibility = true) {
                descriptionText = it
            }
            Button(onClick = { sendPost() },
                Modifier.fillMaxWidth()) {
                Text(text = "Send photo")
            }
        }
    }

}
