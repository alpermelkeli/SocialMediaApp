package com.alpermelkeli.socialmediaapp.components
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import coil.compose.AsyncImagePainter
import coil.compose.rememberAsyncImagePainter

@Composable
fun ShimmerEffectImage(modifier: Modifier, data:String){

    val painter = rememberAsyncImagePainter(model = data)

    val isLoading = painter.state is AsyncImagePainter.State.Loading

    ShimmerEffectBox(
        modifier = modifier,
        isShow = isLoading
    ) {
        Image(
            painter = painter,
            contentDescription = "image",
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.Crop
        )
    }
}