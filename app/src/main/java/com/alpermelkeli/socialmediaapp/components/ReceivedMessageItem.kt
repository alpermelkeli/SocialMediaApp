package com.alpermelkeli.socialmediaapp.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.alpermelkeli.socialmediaapp.R

@Composable
fun ReceivedMessageItem(chat: String, isPrevReceived: Boolean, isNextReceived: Boolean, isEmojiOnly: Boolean, isNextEmojiOnly: Boolean, isPrevEmojiOnly: Boolean) {
    Row(
        horizontalArrangement = Arrangement.Start,
        verticalAlignment = Alignment.Bottom,
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 8.dp, end = 8.dp, bottom = if (!isNextReceived) 0.dp else 24.dp)
    ) {
        var fontSize = 15.sp
        var textModifier = Modifier
            .background(
                color = MaterialTheme.colorScheme.surface,
                shape = RoundedCornerShape(
                    topEnd = 18.dp,
                    bottomEnd = 18.dp,
                    topStart = if (isPrevReceived || isPrevEmojiOnly) 18.dp else 3.dp,
                    bottomStart = if (isNextReceived || isNextEmojiOnly) 18.dp else 3.dp
                )
            )
            .padding(vertical = 8.dp, horizontal = 12.dp)
        if(isEmojiOnly) {
            fontSize = 36.sp
            textModifier = Modifier
                .padding(vertical = 8.dp, horizontal = 12.dp)
        }
        Box(
            contentAlignment = Alignment.CenterStart,
            modifier = Modifier.weight(0.9f)
        ) {
            Text(
                text = chat,
                color = MaterialTheme.colorScheme.onSurface,
                modifier = textModifier,
                fontSize = fontSize
            )
        }
        Spacer(Modifier.weight(0.1f))
    }
}