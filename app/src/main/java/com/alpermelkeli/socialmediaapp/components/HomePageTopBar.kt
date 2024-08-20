package com.alpermelkeli.socialmediaapp.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Send
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.alpermelkeli.socialmediaapp.R


@Composable
fun HomePageTopBar(isDark:Boolean, onClickMessages:()->Unit, onClickCamera: ()-> Unit){
    Box(
        Modifier
            .fillMaxWidth()
            .height(80.dp)
            .background(MaterialTheme.colorScheme.background),
        contentAlignment = Alignment.BottomCenter) {

        Row(
            Modifier
                .fillMaxWidth()
                .height(70.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {

            Box(
                modifier = Modifier.width(70.dp),
                contentAlignment = Alignment.Center
            ) {
                Icon(imageVector = ImageVector.vectorResource(id = R.drawable.camera_icon),
                    contentDescription = "camera",
                    modifier = Modifier.clickable { onClickCamera() },
                    tint = MaterialTheme.colorScheme.secondary)
            }

            Image(
                imageVector = ImageVector.vectorResource(id = if (isDark) R.drawable.instagram_logo_white else R.drawable.instagram_logo_black),
                contentDescription = "logo",
                Modifier.size(110.dp)
            )

            Row(
                modifier = Modifier.width(70.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    imageVector = ImageVector.vectorResource(id = R.drawable.tv_icon),
                    contentDescription = "tv",
                    modifier = Modifier.size(30.dp),
                    tint = MaterialTheme.colorScheme.secondary
                )
                Spacer(modifier = Modifier.width(5.dp))
                Icon(imageVector = ImageVector.vectorResource(id = R.drawable.message_icon), contentDescription = "messages",
                    modifier = Modifier
                        .size(30.dp)
                        .clickable { onClickMessages() },
                    tint = MaterialTheme.colorScheme.secondary)
            }


        }

    }

}