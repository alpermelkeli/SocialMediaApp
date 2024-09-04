package com.alpermelkeli.socialmediaapp.navigation

sealed class NavRoutes(val route:String){
    object Splash: NavRoutes("splash")
    object Login : NavRoutes("login")
    object LoginWithField : NavRoutes("loginwithfield/{email}")
    object SignUp : NavRoutes("signup")
    object Home : NavRoutes("home")
    object UserPost : NavRoutes("userpost/{index}/{isTarget}")
    object Camera : NavRoutes("camera")
    object SendPost : NavRoutes("sendpost/{uri}")
    object TargetProfile : NavRoutes("targetprofile/{userId}")
    object TargetStory : NavRoutes("targetstory/{userId}")
    object EditProfile : NavRoutes("editprofile")
    object Messages : NavRoutes("messages")
    object Chat : NavRoutes("chat")
}