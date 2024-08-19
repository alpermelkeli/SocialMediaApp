package com.alpermelkeli.socialmediaapp.navigation

import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.alpermelkeli.socialmediaapp.screens.Camera
import com.alpermelkeli.socialmediaapp.screens.Login
import com.alpermelkeli.socialmediaapp.screens.LoginWithField
import com.alpermelkeli.socialmediaapp.screens.SignUp
import com.alpermelkeli.socialmediaapp.screens.UserPost
import com.alpermelkeli.socialmediaapp.viewmodel.PostsViewModel
import com.alpermelkeli.socialmediaapp.viewmodel.UserViewModel

@Composable
fun AppNavHost(navController:NavHostController, startDestination:String, initialTab:HomeRoutes){

    NavHost(navController = navController , startDestination = startDestination) {
        composable(NavRoutes.Login.route){
            Login(
                onClickedSwitchAccounts = { navController.navigate(NavRoutes.LoginWithField.route) },
                onClickedLogin = { navController.navigate(NavRoutes.LoginWithField.route) },
                onClickedSignUp = { navController.navigate(NavRoutes.LoginWithField.route) },
            )
        }
        composable(NavRoutes.LoginWithField.route){
            LoginWithField(
                onClickBack = {navController.popBackStack()},
                onClickForgotPassword = {},
                onClickLogin = {navController.navigate(NavRoutes.Home.route)},
                onClickSignUp = {},
            )
        }
        composable(
            route = NavRoutes.UserPost.route,
            arguments = listOf(navArgument("id") {
                type = NavType.StringType
            })
        ) { backStackEntry ->
            val postIndex = backStackEntry.arguments?.getString("id")
            postIndex?.let {
                UserPost(postIndex = it.toInt(), onBackClicked = { navController.popBackStack() })
            }
        }
        composable(NavRoutes.SignUp.route){
            SignUp()
        }
        composable(NavRoutes.Home.route){
            HomeNavContainer(initialTab = initialTab, onNavigate = {navController.navigate(it)})
        }
        composable(NavRoutes.Camera.route){
            Camera()
        }
    }


}