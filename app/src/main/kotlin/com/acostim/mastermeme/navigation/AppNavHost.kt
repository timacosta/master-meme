package com.acostim.mastermeme.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import kotlinx.serialization.Serializable


sealed interface Destinations {
    @Serializable
    data object Home: Destinations
}

@Composable
fun AppNavHost(
    navHostController: NavHostController
) {
    NavHost(
        startDestination =
    )
}