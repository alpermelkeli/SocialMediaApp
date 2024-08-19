package com.alpermelkeli.socialmediaapp.screens

import android.net.Uri
import android.os.Bundle
import android.widget.Toast
import androidx.camera.core.ImageCapture
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import coil.compose.rememberImagePainter
import com.alpermelkeli.socialmediaapp.utils.FetchUsingCamera
import com.alpermelkeli.socialmediaapp.utils.takePhoto

@Composable
fun Camera() {
    var imageCapture: ImageCapture? by remember { mutableStateOf(null) }
    var imageUri by remember { mutableStateOf<Uri?>(null) }

    val context = LocalContext.current

    // Kamera ekranını başlat
    FetchUsingCamera(
        onUseCaseCreated = { capture ->
            imageCapture = capture
        },
        onImageCaptured = { uri ->
            imageUri = uri
            Toast.makeText(context, "Fotoğraf kaydedildi: $uri", Toast.LENGTH_SHORT).show()
        }
    )

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Button(
            onClick = {
                // Fotoğraf çekme işlemini başlat
                imageCapture?.let {
                    takePhoto(context, it) { uri ->
                        imageUri = uri
                        Toast.makeText(context, "Fotoğraf kaydedildi: $uri", Toast.LENGTH_SHORT).show()
                    }
                }
            }
        ) {
            Text(text = "Fotoğraf Çek")
        }

        Spacer(modifier = Modifier.height(16.dp))

        imageUri?.let { uri ->
            Image(painter = rememberImagePainter(uri), contentDescription = "Captured Image")
        }
    }
}
