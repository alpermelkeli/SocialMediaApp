package com.alpermelkeli.socialmediaapp.components

import androidx.compose.foundation.clickable
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
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun UserItem(profilePhoto:String, userName:String, onClickUserItem:()->Unit){

    Row(
        Modifier
            .fillMaxWidth()
            .height(70.dp)
            .clickable { onClickUserItem() },
        horizontalArrangement = Arrangement.Start,
        verticalAlignment = Alignment.CenterVertically) {

        Spacer(modifier = Modifier.width(15.dp))

        ShimmerEffectImage(modifier = Modifier
            .size(48.dp)
            .clip(CircleShape), data = profilePhoto)

        Spacer(modifier = Modifier.width(8.dp))

        Text(text = userName,
            color = MaterialTheme.colorScheme.secondary,
            fontSize = 14.sp)

    }

}