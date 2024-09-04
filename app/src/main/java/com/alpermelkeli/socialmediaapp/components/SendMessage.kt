package com.alpermelkeli.socialmediaapp.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun SentMessageItem(
    chat: String,
    isPrevSent: Boolean,
    isNextSent: Boolean,
    isEmojiOnly: Boolean,
    isNextEmojiOnly: Boolean,
    isPrevEmojiOnly: Boolean
) {
    val backgroundColor = Color(0xFF1261FF)

    Row(
        horizontalArrangement = Arrangement.End,
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 8.dp, end = 8.dp, bottom = if (!isNextSent) 0.dp else 24.dp)
    ) {
        val textModifier = Modifier
            .background(
                color = backgroundColor,
                shape = RoundedCornerShape(
                    topStart = 18.dp,
                    bottomStart = 18.dp,
                    topEnd = if (isPrevSent || isPrevEmojiOnly) 18.dp else 3.dp,
                    bottomEnd = if (isNextSent || isNextEmojiOnly) 18.dp else 3.dp
                )
            )
            .padding(vertical = 8.dp, horizontal = 12.dp)

        val fontSize = if (isEmojiOnly) 36.sp else 15.sp

        Spacer(Modifier.weight(0.2f))

        Box(
            contentAlignment = Alignment.CenterEnd,
            modifier = Modifier.weight(0.8f)
        ) {
            Text(
                text = chat,
                color = Color.White,
                modifier = textModifier,
                fontSize = fontSize
            )
        }
    }
}
