package com.alpermelkeli.socialmediaapp.navigation

import android.net.Uri
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.alpermelkeli.socialmediaapp.screens.Camera
import com.alpermelkeli.socialmediaapp.screens.Login
import com.alpermelkeli.socialmediaapp.screens.LoginWithField
import com.alpermelkeli.socialmediaapp.screens.SendPost
import com.alpermelkeli.socialmediaapp.screens.SignUp
import com.alpermelkeli.socialmediaapp.screens.TargetProfile
import com.alpermelkeli.socialmediaapp.screens.UserPost

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
            arguments = listOf(navArgument("index") {
                type = NavType.StringType
            }, navArgument("isTarget"){
                type = NavType.BoolType
            })
        ) { backStackEntry ->
            val postIndex = backStackEntry.arguments?.getString("index")
            val isTarget = backStackEntry.arguments?.getBoolean("isTarget")
            postIndex?.let {postIndex->
                isTarget?.let {
                    UserPost(postIndex = postIndex.toInt(), isTarget = it, onBackClicked = { navController.popBackStack() }, onClickedPostProfile = {navController.navigate("targetprofile/$it")})
                }
            }
        }

        composable(route = NavRoutes.SendPost.route,
            arguments = listOf(navArgument("uri"){
                type = NavType.StringType
            })
        ) { backStackEntry ->
            val imageUri = backStackEntry.arguments?.getString("uri")
            imageUri?.let {
                SendPost(imageUri, onPostSent = {navController.popBackStack(route = NavRoutes.Home.route, inclusive = false)})
            }
        }

        composable(NavRoutes.SignUp.route){
            SignUp()
        }

        composable(NavRoutes.TargetProfile.route,
            arguments = listOf(navArgument("userId"){
                type = NavType.StringType
            })
        ){backStackEntry ->
            val userId = backStackEntry.arguments?.getString("userId")
            userId?.let {
                TargetProfile(targetUserId = it, onClickedPost = {navController.navigate("userpost/$it/true")})
            }

        }

        composable(NavRoutes.Home.route){
            HomeNavContainer(initialTab = initialTab, onNavigate = {navController.navigate(it)})
        }

        composable(NavRoutes.Camera.route){
            Camera(onPhotoSaved = {navController.navigate("sendpost/${Uri.encode(it.toString())}")},
                onClickBack = {navController.popBackStack()})
        }
    }


}