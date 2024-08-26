package com.alpermelkeli.socialmediaapp.navigation

import android.annotation.SuppressLint
import android.content.res.Configuration
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.rememberNavController

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun HomeNavContainer(initialTab:HomeRoutes,
                     onNavigate:(route:String)->Unit){
    val navController = rememberNavController()
    var selectedRoute by remember {
        mutableStateOf(initialTab)
    }
    // listen for destination changes (e.g. back presses)
    DisposableEffect(Unit) {
        val listener = NavController.OnDestinationChangedListener { _, destination, _ ->
            homeRoutes.firstOrNull { it.route == destination.route }
                ?.let { selectedRoute = it }
        }
        navController.addOnDestinationChangedListener(listener)

        onDispose {
            navController.removeOnDestinationChangedListener(listener)
        }
    }

    val orientation = LocalConfiguration.current.orientation


    Scaffold(bottomBar = {
        if (orientation == Configuration.ORIENTATION_PORTRAIT) {
            NavigationBar(
                containerColor = MaterialTheme.colorScheme.background,
            ) {
                homeRoutes.forEach {
                    NavigationBarItem(label = {
                        Text(stringResource(it.stringRes))
                    }, icon = {
                        Icon(it.icon, null)
                    }, selected = it == selectedRoute, onClick = {
                        navController.navigate(it.route){
                            //This for remember previous fragment and don't create new one if there is.
                            popUpTo(navController.graph.findStartDestination().id)
                            launchSingleTop = true
                        }
                    }, colors = NavigationBarItemDefaults.colors().copy(selectedIconColor = MaterialTheme.colorScheme.secondary, selectedIndicatorColor = Color.Transparent, unselectedIconColor = Color.Gray))
                }
            }
        }
    }){
        Row(
            Modifier
                .fillMaxSize()
                .padding(bottom = it.calculateBottomPadding())
        ) {
            HomeNavHost(
                navController = navController,
                onNavigate = onNavigate,
                startDestination = initialTab,
            )
        }
    }

}