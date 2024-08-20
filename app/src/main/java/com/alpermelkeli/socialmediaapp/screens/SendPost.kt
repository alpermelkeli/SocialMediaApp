package com.alpermelkeli.socialmediaapp.screens

import android.net.Uri
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter
import com.alpermelkeli.socialmediaapp.SocialMediaApplication
import com.alpermelkeli.socialmediaapp.components.DefaultTextField
import com.alpermelkeli.socialmediaapp.model.Post

@Composable
fun SendPost(uri: String){
    val context = LocalContext.current.applicationContext as SocialMediaApplication
    val postViewModel = context.postsViewModel
    val userViewModel = context.userViewModel
    val user by userViewModel.user.observeAsState()
    var descriptionText by remember{ mutableStateOf("") }
    val photoUri: Uri = Uri.parse(uri)
    Box(modifier = Modifier
        .fillMaxSize()
        .background(MaterialTheme.colorScheme.background)){
        Column(
            Modifier
                .fillMaxSize()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally) {
            Image(painter = rememberAsyncImagePainter(model = photoUri), contentDescription = "",
                modifier = Modifier
                    .fillMaxWidth()
                    .height(250.dp))

            DefaultTextField(placeHolder = "Description", value = descriptionText, visibility = true) {
                descriptionText = it
            }
            Button(onClick = { postViewModel.uploadUserPost(Post(user!!.id,0, emptyList(),user!!.profilePhoto,user!!.username, System.currentTimeMillis()), photoUri) },
                Modifier.fillMaxWidth()) {
                Text(text = "Send photo")
            }
        }
    }

}
