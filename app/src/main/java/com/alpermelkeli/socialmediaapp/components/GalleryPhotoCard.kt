package com.alpermelkeli.socialmediaapp.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ShapeDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.tooling.preview.Preview
import com.alpermelkeli.socialmediaapp.model.GalleryPhoto
import com.alpermelkeli.socialmediaapp.model.Post

@Composable
fun GalleryPhotoCard(galleryPhoto: GalleryPhoto, modifier: Modifier, onClickedGalleryPhoto:(GalleryPhoto)->Unit){
    Card(
        modifier = modifier.clickable { onClickedGalleryPhoto(galleryPhoto) },
        colors = CardDefaults.cardColors().copy(containerColor = MaterialTheme.colorScheme.background),
        shape = ShapeDefaults.ExtraSmall,
    ) {
        Box(modifier = Modifier.fillMaxSize()){

            ShimmerEffectImage(modifier = Modifier.fillMaxSize(), data = galleryPhoto.uri.toString())
            if(galleryPhoto.isSelected) {
                Icon(
                    imageVector = Icons.Default.Check,
                    contentDescription = "selected",
                    modifier = Modifier.align(
                        Alignment.TopEnd
                    ).background(MaterialTheme.colorScheme.tertiary)
                        .clip(CircleShape),
                    tint = MaterialTheme.colorScheme.secondary
                )
            }
        }


    }
}