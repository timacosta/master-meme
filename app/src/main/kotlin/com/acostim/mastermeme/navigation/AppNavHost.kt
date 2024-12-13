package com.acostim.mastermeme.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.acostim.mastermeme.home.presentation.HomeRoute
import kotlinx.serialization.Serializable


sealed interface Destinations {
    @Serializable
    data object Home: Destinations
}

@Composable
fun AppNavHost(
    navController: NavHostController,
    modifier: Modifier,
) {
    NavHost(
        navController = navController,
        startDestination = Destinations.Home,
        modifier = modifier
    ) {
        composable<Destinations.Home> {
            HomeRoute()
        }
    }
}