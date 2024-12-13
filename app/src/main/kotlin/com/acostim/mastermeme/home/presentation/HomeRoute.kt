package com.acostim.mastermeme.home.presentation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import org.koin.androidx.compose.koinViewModel

@Composable
fun HomeRoute(
    viewModel: HomeViewModel = koinViewModel()
) {
    HomeScreen()
}