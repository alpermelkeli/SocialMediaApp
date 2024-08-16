package com.alpermelkeli.socialmediaapp.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.alpermelkeli.socialmediaapp.screens.HomePage
import com.alpermelkeli.socialmediaapp.screens.Profile
import com.alpermelkeli.socialmediaapp.screens.Search
import com.alpermelkeli.socialmediaapp.viewmodel.PostsViewModel
import com.alpermelkeli.socialmediaapp.viewmodel.UserViewModel

@Composable
fun HomeNavHost(navController: NavHostController,
                onNavigate: (route: String) -> Unit,
                startDestination: HomeRoutes,
                postsViewModel: PostsViewModel,
                userViewModel:UserViewModel){

    NavHost(navController = navController, startDestination = startDestination.route) {
        composable(HomeRoutes.HomePage.route){
            HomePage(postsViewModel,userViewModel)
        }
        composable(HomeRoutes.Profile.route){
            Profile(onClickedLogOut = {onNavigate(NavRoutes.Login.route)})
        }
        composable(HomeRoutes.Search.route){
            Search()
        }
    }
}