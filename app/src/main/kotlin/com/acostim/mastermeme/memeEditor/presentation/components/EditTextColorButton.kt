package com.acostim.mastermeme.memeEditor.presentation.components

import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.acostim.mastermeme.R

@Composable
fun EditTextColorButton(
    onClick: () -> Unit
) {
    IconButton(
        modifier = Modifier.size(48.dp),
        onClick = {
            onClick()
        }
    ) {
        Icon(
            modifier = Modifier.size(30.dp),
            painter = painterResource(R.drawable.color_picker),
            tint = Color.Unspecified,
            contentDescription = null
        )
    }
}