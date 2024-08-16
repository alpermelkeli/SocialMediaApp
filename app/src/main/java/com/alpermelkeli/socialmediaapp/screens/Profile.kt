package com.alpermelkeli.socialmediaapp.screens

import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import com.alpermelkeli.socialmediaapp.repository.AuthOperations

@Composable
fun Profile(onClickedLogOut:()->Unit){
    Button(onClick = {AuthOperations.logOut()
        onClickedLogOut()
    }) {


    }
}