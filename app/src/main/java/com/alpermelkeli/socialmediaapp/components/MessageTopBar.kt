package com.alpermelkeli.socialmediaapp.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.alpermelkeli.socialmediaapp.SocialMediaApplication

@Composable
fun MessageTopBar(onClickBack:()->Unit,onClickAddMessage:()->Unit){
    val application = LocalContext.current.applicationContext as SocialMediaApplication

    val user by application.userViewModel.user.observeAsState()

    Row(
        Modifier
            .fillMaxWidth()
            .padding(horizontal = 8.dp)
            .padding(top = 50.dp)
            .height(55.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically) {

        IconButton(onClick = { onClickBack() }) {
            Icon(imageVector = Icons.Default.ArrowBack, contentDescription = "Back",
                tint = MaterialTheme.colorScheme.secondary)
        }

        Text(text = user?.username.toString(),
            color = MaterialTheme.colorScheme.secondary,
            fontWeight = FontWeight.SemiBold,
            fontSize = 16.sp
        )

        IconButton(onClick = { onClickAddMessage() }) {
            Icon(imageVector = Icons.Default.Add, contentDescription = "Add",
                tint = MaterialTheme.colorScheme.secondary)
        }

    }

}