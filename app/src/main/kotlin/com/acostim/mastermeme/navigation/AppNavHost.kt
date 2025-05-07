package com.acostim.mastermeme.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.toRoute
import com.acostim.mastermeme.memeEditor.presentation.MemeEditorRoute
import com.acostim.mastermeme.memeList.presentation.MemeListRoute
import kotlinx.serialization.Serializable


sealed interface Destinations {
    @Serializable
    data object MemeList : Destinations

    @Serializable
    data class MemeEditor(val path: String) : Destinations
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
                    navController.navigate(Destinations.MemeEditor(path))
                }
            )
        }

        composable<Destinations.MemeEditor> { backStackEntry ->
            val route = backStackEntry.toRoute<Destinations.MemeEditor>()
            MemeEditorRoute(
                path = route.path,
                onNavigateUp = {
                    navController.navigateUp()
                }
            )
        }
    }
}