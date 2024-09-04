package com.alpermelkeli.socialmediaapp.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Send
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.alpermelkeli.socialmediaapp.R

@Composable
fun ChatBottomBar(onSentMessageClicked:(String)->Unit, bottomPadding: Dp) {
    var messageText by remember{ mutableStateOf("") }
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 8.dp, end = 8.dp, bottom = bottomPadding, top = 0.dp)
            .background(
                color = MaterialTheme.colorScheme.surface,
                shape = RoundedCornerShape(50)
            )
            .padding(6.dp)
    ){
        Icon(
            imageVector = ImageVector.vectorResource(id = R.drawable.comment_icon),
            contentDescription = null,
            tint = Color.White,
            modifier = Modifier
                .background(
                    color = Color(0xFF1880ED),
                    shape = CircleShape
                )
                .size(40.dp)
                .padding(6.dp)
        )
        Spacer(Modifier.width(8.dp))

        TextField(value = messageText, onValueChange = {messageText = it},
            colors = TextFieldDefaults.colors().copy(unfocusedContainerColor = Color.Transparent, focusedContainerColor = Color.Transparent, focusedTextColor = MaterialTheme.colorScheme.secondary),
            modifier = Modifier.alpha(0.75f).weight(1f).padding(horizontal = 4.dp))

        Spacer(Modifier.width(8.dp))
        Icon(
            imageVector = ImageVector.vectorResource(id = R.drawable.comment_icon),
            contentDescription = null,
            tint = MaterialTheme.colorScheme.onSurface,
            modifier = Modifier
                .size(40.dp)
                .padding(8.dp)
        )
        Icon(
            imageVector = Icons.Default.Send,
            contentDescription = null,
            tint = MaterialTheme.colorScheme.secondary,
            modifier = Modifier
                .size(40.dp)
                .padding(8.dp)
                .clickable {
                    onSentMessageClicked(messageText)
                    messageText = ""
                }
        )
    }
}