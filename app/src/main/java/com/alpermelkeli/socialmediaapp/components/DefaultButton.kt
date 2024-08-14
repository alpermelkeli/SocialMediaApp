package com.alpermelkeli.socialmediaapp.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ShapeDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.alpermelkeli.socialmediaapp.ui.theme.Blue50

@Composable
fun DefaultButton(modifier: Modifier, isEnabled:Boolean, onClick:()->Unit){

    Button(onClick = { onClick() },
        modifier = modifier,
        shape = ShapeDefaults.ExtraSmall,
        colors = ButtonDefaults.buttonColors().copy(containerColor = Blue50),
        enabled = isEnabled
        ) {
        Text(text = "Log in",
            color = Color.White)
    }


}