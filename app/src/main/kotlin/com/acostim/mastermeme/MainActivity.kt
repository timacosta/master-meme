package com.acostim.mastermeme

import android.graphics.Color
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.SystemBarStyle
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.toArgb
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import com.acostim.mastermeme.ui.theme.ChineseBlack
import com.acostim.mastermeme.ui.theme.MastermemeTheme

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        installSplashScreen()
        enableEdgeToEdge(
            statusBarStyle = SystemBarStyle.auto(Color.TRANSPARENT, Color.TRANSPARENT),
            navigationBarStyle = SystemBarStyle.auto(ChineseBlack.toArgb(), ChineseBlack.toArgb())
        )
        setContent {
            MastermemeTheme {
                MasterMemeApp(modifier = Modifier.fillMaxSize())
            }
        }
    }
}