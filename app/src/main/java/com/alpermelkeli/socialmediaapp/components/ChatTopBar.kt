package com.alpermelkeli.socialmediaapp.components

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.alpermelkeli.socialmediaapp.R

@Composable
fun ChatTopBar(onUpPressed: () -> Unit) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.fillMaxWidth()
    ){
        IconButton(onClick = { onUpPressed() }) {
            Icon(
                imageVector = ImageVector.vectorResource(id = R.drawable.comment_icon),
                contentDescription = null,
                tint = MaterialTheme.colorScheme.onSurface,
                modifier = Modifier
                    .size(56.dp)
                    .padding(12.dp)
            )
        }
        Spacer(Modifier.width(16.dp))
        Surface(
            modifier = Modifier.size(32.dp),
            shape = CircleShape,
            color = Color.Green
        ){
            Icon(
                imageVector = ImageVector.vectorResource(id = R.drawable.comment_icon),
                contentDescription =null,
                tint = Color.White,
                modifier = Modifier
                    .fillMaxSize()
                    .padding(4.dp)
            )
        }
        Spacer(Modifier.width(12.dp))
        Text(
            text = "the_jarvis",
            color = MaterialTheme.colorScheme.onSurface,
            fontWeight = FontWeight.Medium,
            fontSize = 14.sp,
            modifier = Modifier.weight(1f),
            maxLines = 1,
            overflow = TextOverflow.Ellipsis
        )
        Spacer(Modifier.width(12.dp))
        Icon(
            imageVector = ImageVector.vectorResource(id = R.drawable.comment_icon),
            contentDescription = null,
            tint = MaterialTheme.colorScheme.onSurface,
            modifier = Modifier
                .size(56.dp)
                .padding(16.dp)
                .rotate(270f)
        )
        Icon(
            imageVector = ImageVector.vectorResource(id = R.drawable.comment_icon),
            contentDescription = null,
            tint = MaterialTheme.colorScheme.onSurface,
            modifier = Modifier
                .size(56.dp)
                .padding(16.dp)
        )
        Spacer(Modifier.width(8.dp))
    }
}
