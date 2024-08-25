package com.alpermelkeli.socialmediaapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.lifecycleScope
import com.alpermelkeli.socialmediaapp.navigation.HomeRoutes
import com.alpermelkeli.socialmediaapp.navigation.MainNavContainer
import com.alpermelkeli.socialmediaapp.navigation.NavRoutes
import com.alpermelkeli.socialmediaapp.repository.AuthOperations
import com.alpermelkeli.socialmediaapp.ui.theme.SocialMediaAppTheme
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        enableEdgeToEdge()

        setContent {
            SocialMediaAppTheme {
                var startDestination by remember { mutableStateOf(NavRoutes.Splash.route) }

                LaunchedEffect(Unit) {
                    lifecycleScope.launch {
                        delay(2000)
                        startDestination = if (AuthOperations.getUser() != null) {
                            NavRoutes.Home.route
                        } else {
                            NavRoutes.Login.route
                        }
                    }
                }

                MainNavContainer(startDestination = startDestination, initialTab = HomeRoutes.HomePage)
            }
        }
    }
}



