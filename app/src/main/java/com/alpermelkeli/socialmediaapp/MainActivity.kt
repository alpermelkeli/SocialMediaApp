package com.alpermelkeli.socialmediaapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.alpermelkeli.socialmediaapp.navigation.HomeRoutes
import com.alpermelkeli.socialmediaapp.navigation.MainNavContainer
import com.alpermelkeli.socialmediaapp.navigation.NavRoutes
import com.alpermelkeli.socialmediaapp.repository.AuthOperations
import com.alpermelkeli.socialmediaapp.ui.theme.SocialMediaAppTheme

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)


        enableEdgeToEdge()

        var startDestination = NavRoutes.Login.route

        val initialTab = HomeRoutes.HomePage


        if(AuthOperations.getUser() != null){
            startDestination = NavRoutes.Home.route
        }

        setContent {
            SocialMediaAppTheme {
                MainNavContainer(startDestination = startDestination, initialTab = initialTab)
            }
        }
    }
}

