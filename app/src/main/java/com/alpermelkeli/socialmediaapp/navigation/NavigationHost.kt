package com.alpermelkeli.socialmediaapp.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import com.alpermelkeli.socialmediaapp.screens.Login

@Composable
fun NavigationHost(navController:NavHostController){
    NavHost(navController = navController , startDestination = Screen.LoginScreen.route) {
        composable(Screen.LoginScreen.route){
            Login()
        }
    }



}

sealed class Screen(val route:String){

    object LoginScreen : Screen("login")

}