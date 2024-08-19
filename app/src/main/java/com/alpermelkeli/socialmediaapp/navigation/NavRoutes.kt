package com.alpermelkeli.socialmediaapp.navigation

sealed class NavRoutes(val route:String){

    object Login : NavRoutes("login")
    object LoginWithField : NavRoutes("loginwithfield")
    object SignUp : NavRoutes("signup")
    object Home : NavRoutes("home")
    object UserPost : NavRoutes("userpost/{id}")
    object Camera : NavRoutes("camera")

}