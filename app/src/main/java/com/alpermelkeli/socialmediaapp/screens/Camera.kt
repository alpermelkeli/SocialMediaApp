package com.alpermelkeli.socialmediaapp.screens

import android.annotation.SuppressLint
import android.net.Uri
import android.os.Bundle
import android.widget.Toast
import androidx.camera.core.CameraSelector
import androidx.camera.core.ImageCapture
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountBox
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.rememberImagePainter
import com.alpermelkeli.socialmediaapp.R
import com.alpermelkeli.socialmediaapp.utils.FetchUsingCamera
import com.alpermelkeli.socialmediaapp.utils.takePhoto

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun Camera(onPhotoSaved:(Uri)->Unit, onClickBack:()->Unit) {
    val context = LocalContext.current

    var imageCapture: ImageCapture? by remember { mutableStateOf(null) }

    var cameraSelector by remember { mutableStateOf(CameraSelector.DEFAULT_BACK_CAMERA) }

    var isFlashOpen by remember { mutableStateOf(false) }

    val toggleCamera = {
        cameraSelector = if (cameraSelector == CameraSelector.DEFAULT_BACK_CAMERA) {
            CameraSelector.DEFAULT_FRONT_CAMERA
        } else {
            CameraSelector.DEFAULT_BACK_CAMERA
        }
    }
    val toggleFlash = {
        isFlashOpen = !isFlashOpen
    }
    Scaffold {
        Box(
            Modifier
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.background)
                .padding(bottom = it.calculateBottomPadding(),
                    top = it.calculateTopPadding()),
            contentAlignment = Alignment.Center) {

            FetchUsingCamera(
                cameraSelector = cameraSelector,
                onUseCaseCreated = { capture ->
                    imageCapture = capture
                }
            )


            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 10.dp, vertical = 10.dp),
                verticalArrangement = Arrangement.SpaceBetween,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Row(
                    Modifier
                        .fillMaxWidth()
                        .height(80.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically) {
                    IconButton(onClick = { onClickBack() }) {

                        Icon(imageVector = Icons.Default.Close, contentDescription = "close",
                            tint = MaterialTheme.colorScheme.secondary)
                    }

                    IconButton(onClick = { toggleFlash() }) {
                        Icon(imageVector = ImageVector.vectorResource(id = R.drawable.flash_button), contentDescription = "Flash",
                            tint = MaterialTheme.colorScheme.secondary)
                    }

                }
                Row(
                    Modifier
                        .fillMaxWidth()
                        .height(100.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically) {

                    IconButton(onClick = { /*TODO*/ },
                        Modifier.size(40.dp)) {
                        Icon(modifier = Modifier.size(40.dp),
                            imageVector = Icons.Default.AccountBox, contentDescription = "Gallery",
                            tint = MaterialTheme.colorScheme.secondary
                        )
                    }

                    Button(
                        colors = ButtonDefaults.buttonColors().copy(containerColor = MaterialTheme.colorScheme.secondary),
                        modifier = Modifier.size(75.dp),
                        onClick = {
                            imageCapture?.let {
                                takePhoto(context, it) { uri ->
                                    onPhotoSaved(uri)
                                }
                            }
                        }
                    ) {
                        Icon(modifier = Modifier.size(75.dp),
                            imageVector = ImageVector.vectorResource(id = R.drawable.take_photo_button), contentDescription = "take photo",
                            tint = MaterialTheme.colorScheme.secondary)

                    }

                    IconButton(onClick = { toggleCamera() },
                        modifier = Modifier.size(40.dp)) {
                        Icon(modifier = Modifier.size(40.dp),imageVector = Icons.Default.Refresh, contentDescription = "camera",
                            tint = MaterialTheme.colorScheme.secondary)
                    }

                }



            }
        }
    }




}
