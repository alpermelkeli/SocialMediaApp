package com.alpermelkeli.socialmediaapp.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberAsyncImagePainter

@Composable
fun UserItem(profilePhoto:String, userName:String){

    Row(
        Modifier
            .fillMaxWidth()
            .height(70.dp),
        horizontalArrangement = Arrangement.Start,
        verticalAlignment = Alignment.CenterVertically) {

        Spacer(modifier = Modifier.width(15.dp))

        Image(painter = rememberAsyncImagePainter(model = profilePhoto), contentDescription = "",
            modifier = Modifier
                .size(48.dp)
                .clip(CircleShape))

        Spacer(modifier = Modifier.width(8.dp))

        Text(text = userName,
            color = MaterialTheme.colorScheme.secondary,
            fontSize = 14.sp)

    }

}