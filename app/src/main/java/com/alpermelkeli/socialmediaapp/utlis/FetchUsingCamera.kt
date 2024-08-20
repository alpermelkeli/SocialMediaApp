package com.alpermelkeli.socialmediaapp.utils

import android.content.Context
import android.net.Uri
import android.os.Environment
import android.util.Log
import androidx.camera.core.*
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.camera.view.PreviewView
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.viewinterop.AndroidView
import androidx.core.content.ContextCompat
import java.io.File
import java.text.SimpleDateFormat
import java.util.*

@Composable
fun FetchUsingCamera(
    onUseCaseCreated: (ImageCapture) -> Unit,
    cameraSelector: CameraSelector
) {
    val context = LocalContext.current
    var imageCapture: ImageCapture? by remember { mutableStateOf(null) }
    CameraPreview(
        context = context,
        cameraSelector = cameraSelector,
        onUseCaseCreated = { useCase ->
            imageCapture = useCase
            onUseCaseCreated(useCase) // Pass the ImageCapture instance back to the caller
        }
    )

    // If imageCapture is not null, it means the CameraPreview was successfully set up
    LaunchedEffect(imageCapture) {
        // ImageCapture is available to be used
    }

}

@Composable
fun CameraPreview(
    context: Context,
    cameraSelector: CameraSelector,
    onUseCaseCreated: (ImageCapture) -> Unit
) {
    val cameraProviderFuture = remember { ProcessCameraProvider.getInstance(context) }
    AndroidView(
        modifier = Modifier.fillMaxWidth().fillMaxHeight(0.8f),
        factory = { ctx ->
            val previewView = PreviewView(ctx)
            val executor = ContextCompat.getMainExecutor(ctx)
            cameraProviderFuture.addListener({
                val cameraProvider = cameraProviderFuture.get()
                val preview = Preview.Builder().build()
                val imageCapture = ImageCapture.Builder().build()
                onUseCaseCreated(imageCapture)

                preview.setSurfaceProvider(previewView.surfaceProvider)

                try {
                    cameraProvider.unbindAll()
                    cameraProvider.bindToLifecycle(
                        context as androidx.lifecycle.LifecycleOwner,
                        cameraSelector,
                        preview,
                        imageCapture
                    )
                } catch (e: Exception) {
                    Log.e("CameraPreview", "Camera couldn't started: ${e.message}")
                }
            }, executor)
            previewView
        }
    )
}

fun takePhoto(
    context: Context,
    imageCapture: ImageCapture?,
    onImageCaptured: (Uri) -> Unit
) {
    val photoFile = File(
        context.getExternalFilesDir(Environment.DIRECTORY_PICTURES),
        SimpleDateFormat("yyyyMMdd_HHmmss", Locale.US).format(Date()) + ".jpg"
    )

    val outputOptions = ImageCapture.OutputFileOptions.Builder(photoFile).build()
    imageCapture?.takePicture(
        outputOptions,
        ContextCompat.getMainExecutor(context),
        object : ImageCapture.OnImageSavedCallback {
            override fun onError(exc: ImageCaptureException) {
                Log.e("CameraX", "Error: ${exc.message}", exc)
            }

            override fun onImageSaved(output: ImageCapture.OutputFileResults) {
                val savedUri = Uri.fromFile(photoFile)
                onImageCaptured(savedUri)
            }
        }
    )
}
