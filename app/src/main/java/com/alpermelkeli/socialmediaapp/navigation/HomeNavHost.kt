package com.alpermelkeli.socialmediaapp.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.alpermelkeli.socialmediaapp.screens.HomePage
import com.alpermelkeli.socialmediaapp.screens.Profile
import com.alpermelkeli.socialmediaapp.screens.Search

@Composable
fun HomeNavHost(navController: NavHostController,
                onNavigate: (route: String) -> Unit,
                startDestination: HomeRoutes){

    val onClickedOwn = {
        navController.navigate(HomeRoutes.Profile.route){ popUpTo(navController.graph.findStartDestination().id)
            launchSingleTop = true
        }
    }

    NavHost(navController = navController, startDestination = startDestination.route) {
        composable(HomeRoutes.HomePage.route){
            HomePage(onCameraClicked = {onNavigate(NavRoutes.Camera.route)}, onClickedPostProfile = {onNavigate("targetprofile/$it")}, onClickedOwnPostProfile = {onClickedOwn()})
        }
        composable(HomeRoutes.Profile.route){
            Profile(onClickedLogOut = {onNavigate(NavRoutes.Login.route)}, onClickedPost = {onNavigate("userpost"+"/$it/false")})
        }
        composable(HomeRoutes.Search.route){
            Search(onUserItemClicked = {onNavigate("targetprofile" + "/$it")}, onClickedOwnUserItem = {onClickedOwn()})
        }
    }
}