package com.alpermelkeli.socialmediaapp

import androidx.compose.runtime.Composable
import androidx.navigation.compose.rememberNavController
import com.alpermelkeli.socialmediaapp.navigation.NavigationHost

@Composable
fun SocialMediaApp(){
    val navController = rememberNavController()
    NavigationHost(navController = navController)
}