package com.alpermelkeli.socialmediaapp.components

import android.annotation.SuppressLint
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp


@SuppressLint("UnrememberedMutableState")
@Composable
fun StoryTopBar(
    currentStoryIndex: Int,
    totalStories: Int,
    username: String,
    profilePhoto: String,
    onBackClicked: () -> Unit,
    onTimeFinished: () -> Unit
) {
    val progressValues = remember(totalStories) {
        generateProgressValues(totalStories)
    }

    LaunchedEffect(currentStoryIndex, totalStories) {
        progressValues.forEachIndexed { index, animatable ->
            if (index != currentStoryIndex) {
                animatable.snapTo(0f)
            }
        }

        progressValues.getOrNull(currentStoryIndex)?.let { animatable ->
            animatable.animateTo(
                targetValue = 1f,
                animationSpec = tween(durationMillis = 5000, easing = LinearEasing)
            )
            onTimeFinished()
        }
    }

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(80.dp)
            .background(Color.Transparent)
            .padding(top = 30.dp)
    ) {
        Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Center) {
            for (i in 0 until totalStories) {
                LinearProgressIndicator(
                    progress = progressValues.getOrNull(i)?.value ?: 0f,
                    modifier = Modifier
                        .padding(2.dp)
                        .weight(1f),
                    color = Color.White
                )
            }
        }

        Row(
            Modifier
                .fillMaxSize()
                .padding(horizontal = 10.dp)
                .padding(top = 10.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                ShimmerEffectImage(
                    modifier = Modifier
                        .size(35.dp)
                        .clip(CircleShape),
                    data = profilePhoto
                )
                Spacer(modifier = Modifier.width(10.dp))

                Text(
                    text = username,
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.White
                )
            }
            IconButton(onClick = { onBackClicked() }) {
                Icon(
                    imageVector = Icons.Default.Close,
                    contentDescription = "back",
                    tint = Color.White
                )
            }
        }
    }
}

private fun generateProgressValues(totalStories: Int): List<Animatable<Float, *>> {
    return List(totalStories) { Animatable(0f) }
}
