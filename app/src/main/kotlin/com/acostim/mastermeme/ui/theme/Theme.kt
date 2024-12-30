package com.acostim.mastermeme.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.runtime.Composable

private val colorScheme = darkColorScheme(
    primary = Primary,
    primaryContainer = PrimaryContainer,
    background = Background,
    surface = Surface,
    onSurface = OnSurface
)

@Composable
fun MastermemeTheme(
    content: @Composable () -> Unit
) {
    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}