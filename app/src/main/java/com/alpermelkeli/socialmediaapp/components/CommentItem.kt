package com.alpermelkeli.socialmediaapp.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material3.BottomSheetScaffold
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberAsyncImagePainter
import com.alpermelkeli.socialmediaapp.R
import com.alpermelkeli.socialmediaapp.model.Comment

@Composable
fun CommentItem(comment:Comment){
    Row(
        Modifier
            .fillMaxWidth()
            .padding(horizontal = 5.dp)
            .padding(top = 20.dp),) {
        Image(painter = rememberAsyncImagePainter(model = comment.senderPhoto), contentDescription = "",
            modifier = Modifier
                .size(50.dp)
                .clip(CircleShape))
        Spacer(modifier = Modifier.width(10.dp))

        Column(
            Modifier
                .width(250.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.Start) {
            Text(text = comment.senderUsername,
                fontSize = 12.sp,
                color = MaterialTheme.colorScheme.secondary)
            Text(text = comment.content,
                color = MaterialTheme.colorScheme.secondary)
        }
        Spacer(modifier = Modifier.width(20.dp))

        IconButton(onClick = {  }) {

        Icon(imageVector = Icons.Default.FavoriteBorder, contentDescription = "favorite",
            tint = MaterialTheme.colorScheme.secondary)

        }

    }
}