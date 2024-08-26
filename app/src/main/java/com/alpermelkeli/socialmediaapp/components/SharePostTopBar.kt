package com.alpermelkeli.socialmediaapp.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun SharePostTopBar(onBackClicked:()->Unit,onNextClicked:()->Unit){
    Row(
        Modifier
            .fillMaxWidth()
            .height(90.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically) {

        Icon(imageVector = Icons.Default.ArrowBack, contentDescription = "back", modifier = Modifier
            .size(30.dp)
            .clickable { onBackClicked() },
            tint = MaterialTheme.colorScheme.secondary)

        Text(text = "New Post",
            fontWeight = FontWeight.Bold,
            textAlign = TextAlign.Start,
            fontSize = 16.sp,
            color = MaterialTheme.colorScheme.secondary)

        Text(text = "Next",
            Modifier.width(50.dp).clickable { onNextClicked() },
            color = MaterialTheme.colorScheme.tertiary,
            fontWeight = FontWeight.Bold)
    }
}