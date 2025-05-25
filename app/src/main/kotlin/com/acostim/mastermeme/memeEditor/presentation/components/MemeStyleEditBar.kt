package com.acostim.mastermeme.memeEditor.presentation.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.acostim.mastermeme.R
import com.acostim.mastermeme.ui.theme.MastermemeTheme

@Composable
fun MemeStyleEditBar(modifier: Modifier = Modifier) {
    Row(
        modifier = modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceEvenly
    ) {
        CloseButton()

        Spacer(Modifier.width(12.dp))

        EditTextStyleButton()

        EditTextSizeButton()

        EditTextColorButton()

        Spacer(Modifier.width(12.dp))

        AcceptButton()
    }
}

@Composable
private fun EditTextStyleButton() {
    IconButton(
        onClick = {

        }
    ) {
        Icon(
            painter = painterResource(R.drawable.ic_text_style_button),
            contentDescription = null
        )
    }
}

@Composable
private fun EditTextSizeButton() {
    IconButton(
        onClick = {

        }
    ) {
        Icon(
            painter = painterResource(R.drawable.ic_text_size_button),
            contentDescription = null
        )
    }
}


@Composable
private fun EditTextColorButton() {
    IconButton(
        modifier = Modifier.size(48.dp),
        onClick = {

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


@Composable
private fun CloseButton() {
    IconButton(
        onClick = {

        }
    ) {
        Icon(
            imageVector = Icons.Default.Close,
            contentDescription = null
        )
    }
}

@Composable
private fun AcceptButton() {
    IconButton(onClick = {

    }) {
        Icon(
            imageVector = Icons.Default.Check,
            contentDescription = null
        )
    }
}

@Preview
@Composable
private fun MemeStyleEditBarPreview() {
    MastermemeTheme {
        Surface {
            MemeStyleEditBar()
        }
    }
}