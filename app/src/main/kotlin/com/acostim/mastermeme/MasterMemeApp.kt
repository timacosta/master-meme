package com.acostim.mastermeme

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.acostim.mastermeme.navigation.AppNavHost

@Composable
fun MasterMemeApp(
    navController: NavHostController = rememberNavController(),
    modifier: Modifier = Modifier
) {
    AppNavHost(
        navController = navController,
        modifier = modifier
    )
}
