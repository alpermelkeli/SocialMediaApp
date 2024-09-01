package com.alpermelkeli.socialmediaapp.components

import android.animation.ArgbEvaluator
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.LayoutCoordinates
import androidx.compose.ui.layout.boundsInParent
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun SentMessageItem(chat: String, isPrevSent: Boolean, isNextSent: Boolean, isEmojiOnly: Boolean, isNextEmojiOnly: Boolean, isPrevEmojiOnly: Boolean, listHeight: Float) {

    val evaluator = remember { ArgbEvaluator() }
    val topShade = remember { Color(0xFFB500E7) }
    val bottomShade = remember { Color(0xFF1261FF) }
    var backgroundColor by remember { mutableStateOf(bottomShade) }

    Row(
        horizontalArrangement = Arrangement.End,
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 8.dp, end = 8.dp, bottom = if (!isNextSent) 0.dp else 24.dp)
            .onGloballyPositioned { coordinates: LayoutCoordinates ->
                if (listHeight > 0f) {
                    val topOffset = coordinates.boundsInParent().top
                    val cleanTopOffset = when {
                        topOffset < 0 -> 0f
                        topOffset > listHeight -> listHeight
                        else -> topOffset
                    }
                    /*backgroundColor = getColorAtProgress(
                        progress = cleanTopOffset / listHeight,
                        start = topShade,
                        end = bottomShade,
                        evaluator = evaluator
                    )*/
                }
            }
    ) {
        var fontSize = 15.sp
        var textModifier = Modifier
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

        if(isEmojiOnly) {
            fontSize = 36.sp
            textModifier = Modifier
                .padding(vertical = 8.dp, horizontal = 12.dp)
        }

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