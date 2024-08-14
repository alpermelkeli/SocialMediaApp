package com.alpermelkeli.socialmediaapp.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun BackTopBar(text: String ?= null){

    Row(
        Modifier
            .fillMaxSize()
            .height(50.dp),
        horizontalArrangement = Arrangement.SpaceBetween) {

        Icon(imageVector = Icons.Default.ArrowBack, contentDescription = "back", modifier = Modifier
            .padding(start = 10.dp, top = 10.dp)
            .size(30.dp),
            tint = MaterialTheme.colorScheme.secondary)

        if(text != null){
            Text(text = text)
            Spacer(modifier = Modifier.width(10.dp))
        }

    }

}