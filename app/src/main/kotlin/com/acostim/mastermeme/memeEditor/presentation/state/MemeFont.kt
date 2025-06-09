package com.acostim.mastermeme.memeEditor.presentation.state

import androidx.compose.ui.text.font.FontFamily
import com.acostim.mastermeme.ui.theme.Impact
import com.acostim.mastermeme.ui.theme.Manrope
import com.acostim.mastermeme.ui.theme.Roboto_Bold
import com.acostim.mastermeme.ui.theme.Roboto_Thin

data class MemeFont(
    val fontFamily: FontFamily,
    val fontFamilyName: String,
    val hasBorder: Boolean = false,
    val text: String = "GOOD",
)

val fonts = listOf(
    MemeFont(
        fontFamily = Manrope,
        fontFamilyName = "Manrope"
    ),
    MemeFont(
        fontFamily = Impact,
        fontFamilyName = "Impact"
    ),
    MemeFont(
        fontFamily = Roboto_Thin,
        "Roboto"
    ),
    MemeFont(
        fontFamily = Roboto_Bold,
        "Shadowed"
    ),
)