package com.alpermelkeli.socialmediaapp.screens

import android.annotation.SuppressLint
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.alpermelkeli.socialmediaapp.components.BackTopBar
import com.alpermelkeli.socialmediaapp.ui.theme.SocialMediaAppTheme

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Preview
@Composable
fun LoginWithFields(){

    SocialMediaAppTheme {
        val darkTheme = isSystemInDarkTheme()
        Scaffold(Modifier.fillMaxSize(), topBar = { BackTopBar() }) {

            Column(Modifier.fillMaxSize()) {




            }
        }
    }
}