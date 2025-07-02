package com.acostim.mastermeme

import android.app.Activity
import android.graphics.Color
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.SystemBarStyle
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalView
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.core.view.WindowCompat
import com.acostim.mastermeme.ui.theme.MastermemeTheme

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        installSplashScreen()
        enableEdgeToEdge(
            statusBarStyle = SystemBarStyle.dark(Color.TRANSPARENT),
            navigationBarStyle = SystemBarStyle.dark(Color.TRANSPARENT)
        )
        super.onCreate(savedInstanceState)

        setContent {
            val currentView = LocalView.current.context
            MastermemeTheme {
                SideEffect {
                    val window = (currentView as Activity).window
                    WindowCompat.getInsetsController(window, window.decorView)
                        .isAppearanceLightNavigationBars = false // inverse for dark themes
                }
                MasterMemeApp(modifier = Modifier.fillMaxSize())
            }
        }
    }
}