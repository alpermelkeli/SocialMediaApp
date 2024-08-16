package com.alpermelkeli.socialmediaapp.navigation

import androidx.annotation.StringRes
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Search
import androidx.compose.ui.graphics.vector.ImageVector
import com.alpermelkeli.socialmediaapp.R

sealed class HomeRoutes(val route:String,
    @StringRes val stringRes:Int,
    val icon: ImageVector
    ){

    object HomePage : HomeRoutes("homepage", R.string.home , Icons.Default.Home)
    object Search : HomeRoutes("search",R.string.search, Icons.Default.Search)
    object Profile : HomeRoutes("profile", R.string.profile, Icons.Default.Person)

}
val homeRoutes = listOf(HomeRoutes.HomePage, HomeRoutes.Search, HomeRoutes.Profile)
