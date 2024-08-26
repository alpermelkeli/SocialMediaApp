package com.alpermelkeli.socialmediaapp.components
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ShapeDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.alpermelkeli.socialmediaapp.model.Post

@Composable
fun ProfilePostCard(post:Post,modifier: Modifier, onClickedPost:(Post)->Unit){
    Card(
        modifier = modifier.clickable { onClickedPost(post) },
        colors = CardDefaults.cardColors().copy(containerColor = MaterialTheme.colorScheme.background),
        shape = ShapeDefaults.ExtraSmall,
    ) {

        ShimmerEffectImage(modifier = Modifier.fillMaxSize(), data = if(post.images.size>0) post.images[0] else "")

    }
}