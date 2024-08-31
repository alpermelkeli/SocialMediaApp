package com.alpermelkeli.socialmediaapp.screens

import android.Manifest
import android.net.Uri
import android.os.Build
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.alpermelkeli.socialmediaapp.SocialMediaApplication
import com.alpermelkeli.socialmediaapp.components.GalleryPhotoCard
import com.alpermelkeli.socialmediaapp.components.SharePostTopBar
import com.alpermelkeli.socialmediaapp.components.ShimmerEffectImage
import com.alpermelkeli.socialmediaapp.model.GalleryPhoto
import com.alpermelkeli.socialmediaapp.model.Post
import java.util.UUID


@Composable
fun SharePost(onPermissionDenied: () -> Unit, onBackClicked:()->Unit, onPostsUploaded:()->Unit) {
    val context = LocalContext.current.applicationContext as SocialMediaApplication

    val galleryViewModel = context.galleryViewModel

    val permissionViewModel = context.permissionViewModel

    val postsViewModel = context.postsViewModel

    val userViewModel = context.userViewModel

    val user by userViewModel.user.observeAsState()

    val photos by galleryViewModel.photos.observeAsState(emptyList())

    val selectedPhotos by galleryViewModel.selectedPhotos.observeAsState(emptyList())

    var selectedPhoto by remember { mutableStateOf(if(photos.isNotEmpty()) photos[0] else GalleryPhoto(Uri.EMPTY,false)) }


    val permissionLauncher = rememberLauncherForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) {
        if (it) {
            galleryViewModel.getPhotos()
        } else {
            onPermissionDenied()
            Toast.makeText(context, "Permission Denied", Toast.LENGTH_SHORT).show()
        }
    }
    val onNextClicked = {
        user?.let {
            val uuid = UUID.randomUUID()
            val newPost = Post(uuid.toString(),it.id, emptyList(),it.profilePhoto,it.username,System.currentTimeMillis())
            postsViewModel.uploadPost(newPost,selectedPhotos.map { it.uri })
            onPostsUploaded()
        }
    }

    LaunchedEffect(Unit) {
        permissionViewModel.checkStoragePermission(
            context,
            onGranted = { galleryViewModel.getPhotos() },
            onUnGranted = { if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                permissionLauncher.launch(Manifest.permission.READ_MEDIA_IMAGES)
            }
                else{
                    permissionLauncher.launch(Manifest.permission.READ_EXTERNAL_STORAGE)
            }
            }
        )
    }
    Scaffold(Modifier.fillMaxSize(), topBar = {SharePostTopBar(onBackClicked = {onBackClicked()}, onNextClicked = {onNextClicked()})}) {
        Column(
            Modifier
                .fillMaxWidth()
                .padding(top = it.calculateTopPadding())) {
            ShimmerEffectImage(modifier = Modifier
                .fillMaxWidth()
                .height(300.dp), data = selectedPhoto.uri.toString())

            Spacer(modifier = Modifier.height(30.dp))

            LazyColumn {

                items(photos.chunked(3)) { rowItems ->
                    Row(
                        modifier = Modifier
                            .fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        rowItems.forEach { galleryPhoto ->
                            GalleryPhotoCard(galleryPhoto, Modifier
                                .weight(1f)
                                .padding(0.5.dp)
                                .height(120.dp)){
                                galleryViewModel.selectPhoto(it)
                                selectedPhoto = it
                            }
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


