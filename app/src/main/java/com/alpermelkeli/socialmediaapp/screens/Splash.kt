package com.alpermelkeli.socialmediaapp.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.dp
import com.alpermelkeli.socialmediaapp.R

@Composable
fun Splash() {
    val isDark = isSystemInDarkTheme()
    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        contentAlignment = Alignment.Center
    ) {
        Image(imageVector = ImageVector.vectorResource(id = if(isDark) R.drawable.instagram_logo_white else R.drawable.instagram_logo_black), contentDescription = "logo")
    }
}