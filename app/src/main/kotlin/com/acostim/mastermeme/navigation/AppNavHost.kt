package com.acostim.mastermeme.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.acostim.mastermeme.createMeme.presentation.CreateMemeRoute
import com.acostim.mastermeme.memeList.presentation.MemeListRoute
import kotlinx.serialization.Serializable


sealed interface Destinations {
    @Serializable
    data object MemeList: Destinations

    @Serializable
    data object CreateMeme: Destinations
}

@Composable
fun AppNavHost(
    navController: NavHostController,
    modifier: Modifier,
) {
    NavHost(
        navController = navController,
        startDestination = Destinations.MemeList,
        modifier = modifier
    ) {
        composable<Destinations.MemeList> {
            MemeListRoute(
                navigateToCreateMeme = {
                    navController.navigate(Destinations.CreateMeme)
                }
            )
        }

        composable<Destinations.CreateMeme> {
            CreateMemeRoute()
        }
    }
}