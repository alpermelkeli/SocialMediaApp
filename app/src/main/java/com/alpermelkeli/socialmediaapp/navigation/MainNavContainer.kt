package com.alpermelkeli.socialmediaapp.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.rememberNavController

@Composable
fun MainNavContainer(startDestination: String, initialTab: HomeRoutes){
    val navController = rememberNavController()

    AppNavHost(navController = navController, startDestination = startDestination, initialTab = initialTab)
}