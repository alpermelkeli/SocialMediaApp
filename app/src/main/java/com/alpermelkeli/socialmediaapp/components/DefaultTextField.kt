package com.alpermelkeli.socialmediaapp.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.ShapeDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import com.alpermelkeli.socialmediaapp.ui.theme.Black50
import com.alpermelkeli.socialmediaapp.ui.theme.Grey20


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DefaultTextField(placeHolder:String,value:String, visibility: Boolean, onValueChange:(String)->Unit){

    OutlinedTextField(
        value = value,
        onValueChange = {onValueChange(it)},
        colors = TextFieldDefaults.outlinedTextFieldColors(
            unfocusedBorderColor = Color.LightGray,
            containerColor = MaterialTheme.colorScheme.background,
            unfocusedTextColor = MaterialTheme.colorScheme.secondary,
            focusedTextColor = MaterialTheme.colorScheme.secondary,
            cursorColor = Black50,
            focusedBorderColor = Color.DarkGray,
            disabledBorderColor = Color.LightGray
        ),
        shape = ShapeDefaults.ExtraSmall,
        placeholder = {
            Text(text = placeHolder, color = Color.LightGray)
        },
        modifier = Modifier
            .fillMaxWidth(0.9f)
            .height(50.dp),
        visualTransformation = if(visibility) VisualTransformation.None else PasswordVisualTransformation()
    )



}