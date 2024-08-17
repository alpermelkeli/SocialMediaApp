package com.alpermelkeli.socialmediaapp.components
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ShapeDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import coil.annotation.ExperimentalCoilApi
import coil.compose.rememberImagePainter
import com.alpermelkeli.socialmediaapp.model.Post

@OptIn(ExperimentalCoilApi::class)
@Composable
fun ProfilePostCard(post:Post,modifier: Modifier){
    Card(
        modifier = modifier,
        colors = CardDefaults.cardColors().copy(containerColor = MaterialTheme.colorScheme.background),
        shape = ShapeDefaults.ExtraSmall,
    ) {
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier.fillMaxSize()
        ) {
            Image(painter = rememberImagePainter(data = post.images[0]), contentDescription = "",
                contentScale = ContentScale.Crop,
                modifier = Modifier.fillMaxSize())
        }

    }
}