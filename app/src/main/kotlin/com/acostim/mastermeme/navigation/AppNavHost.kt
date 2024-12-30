package com.acostim.mastermeme.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.toRoute
import com.acostim.mastermeme.createMeme.presentation.CreateMemeRoute
import com.acostim.mastermeme.memeList.presentation.MemeListRoute
import kotlinx.serialization.Serializable


sealed interface Destinations {
    @Serializable
    data object MemeList: Destinations

    @Serializable
    data class CreateMeme(val path: String): Destinations
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
                navigateToCreateMeme = { path ->
                    navController.navigate(Destinations.CreateMeme(path))
                }
            )
        }

        composable<Destinations.CreateMeme> { backStackEntry ->
            val route = backStackEntry.toRoute<Destinations.CreateMeme>()
            CreateMemeRoute(path = route.path, onNavigateUp = {
                navController.navigateUp()
            })
        }
    }
}