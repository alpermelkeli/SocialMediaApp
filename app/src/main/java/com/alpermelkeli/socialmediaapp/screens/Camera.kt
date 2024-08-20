package com.alpermelkeli.socialmediaapp.screens

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
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.dp
import coil.compose.rememberImagePainter
import com.alpermelkeli.socialmediaapp.R
import com.alpermelkeli.socialmediaapp.utils.FetchUsingCamera
import com.alpermelkeli.socialmediaapp.utils.takePhoto

@Composable
fun Camera(onPhotoSaved:(Uri)->Unit) {
    val context = LocalContext.current

    var imageCapture: ImageCapture? by remember { mutableStateOf(null) }

    var cameraSelector by remember { mutableStateOf(CameraSelector.DEFAULT_BACK_CAMERA) }

    val toggleCamera = {
        cameraSelector = if (cameraSelector == CameraSelector.DEFAULT_BACK_CAMERA) {
            CameraSelector.DEFAULT_FRONT_CAMERA
        } else {
            CameraSelector.DEFAULT_BACK_CAMERA
        }
    }

    Box(
        Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background),
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
                .padding(horizontal = 10.dp, vertical = 20.dp),
            verticalArrangement = Arrangement.SpaceBetween,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Row(
                Modifier
                    .fillMaxWidth()
                    .height(80.dp)) {

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
