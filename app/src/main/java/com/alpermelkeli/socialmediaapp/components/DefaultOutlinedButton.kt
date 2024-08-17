package com.alpermelkeli.socialmediaapp.components

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.ShapeDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
fun DefaultOutlinedButton(modifier: Modifier, text:String, onClick:()->Unit){
    OutlinedButton(onClick = {onClick()},
        modifier = modifier,
        shape = ShapeDefaults.ExtraSmall) {
            Text(text = text,
                color = MaterialTheme.colorScheme.secondary)
    }
}