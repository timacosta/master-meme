package com.acostim.mastermeme.memeEditor.presentation.components

import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.painterResource
import com.acostim.mastermeme.R

@Composable
fun EditTextSizeButton(
    onClick: () -> Unit
) {
    IconButton(
        onClick = {
            onClick()
        }
    ) {
        Icon(
            painter = painterResource(R.drawable.ic_text_size_button),
            contentDescription = null
        )
    }
}