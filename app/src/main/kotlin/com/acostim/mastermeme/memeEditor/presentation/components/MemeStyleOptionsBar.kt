package com.acostim.mastermeme.memeEditor.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.acostim.mastermeme.memeEditor.presentation.MemeFont
import com.acostim.mastermeme.memeEditor.presentation.fonts
import com.acostim.mastermeme.ui.theme.MastermemeTheme
import com.acostim.mastermeme.ui.theme.SurfaceContainer

@Composable
fun MemeStyleEditBar(
    modifier: Modifier = Modifier,
    onFontSelection: (MemeFont) -> Unit,
) {

    var isTextStyleOptionsVisible by remember { mutableStateOf(true) }
    var isTextSizeOptionsVisible by remember { mutableStateOf(false) }
    var isTextColorOptionsVisible by remember { mutableStateOf(false) }

    Column(
        modifier = modifier.background(SurfaceContainer)
    ) {
        if (isTextStyleOptionsVisible) {
            ChangeTextStyleOptions(
                fonts = fonts,
                onFontSelection = { memeFont ->
                    onFontSelection(memeFont)
                }
            )
        }

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            CloseButton(
                onClick = {
                    //TODO
                }
            )

            Spacer(Modifier.width(12.dp))

            EditTextStyleButton(
                onClick = {
                    isTextStyleOptionsVisible = true
                    isTextSizeOptionsVisible = false
                    isTextColorOptionsVisible = false
                }
            )

            EditTextSizeButton(
                onClick = {
                    isTextStyleOptionsVisible = false
                    isTextSizeOptionsVisible = true
                    isTextColorOptionsVisible = false
                }
            )

            EditTextColorButton(
                onClick = {
                    isTextStyleOptionsVisible = false
                    isTextSizeOptionsVisible = false
                    isTextColorOptionsVisible = true
                }
            )

            Spacer(Modifier.width(12.dp))

            AcceptButton(onClick = {
                //TODO
            })
        }
    }
}

@Composable
private fun CloseButton(
    onClick: () -> Unit
) {
    IconButton(
        onClick = {
            onClick()
        }
    ) {
        Icon(
            imageVector = Icons.Default.Close,
            contentDescription = null
        )
    }
}

@Composable
private fun AcceptButton(onClick: () -> Unit) {
    IconButton(onClick = {
        onClick()
    }) {
        Icon(
            imageVector = Icons.Default.Check,
            contentDescription = null
        )
    }
}

@Composable
private fun ChangeTextStyleOptions(
    fonts: List<MemeFont>,
    onFontSelection: (MemeFont) -> Unit,
) {
    LazyRow(
        modifier = Modifier
            .fillMaxWidth()
            .padding(
                top = 8.dp,
                bottom = 8.dp,
                start = 12.dp,
                end = 12.dp
            ),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        items(fonts) {
            Column(
                modifier = Modifier.clickable {
                    onFontSelection(it)
                },
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = it.text,
                    fontFamily = it.fontFamily,
                    fontSize = 28.sp
                )

                Text(
                    text = it.fontFamilyName,
                    fontFamily = it.fontFamily,
                    fontSize = 12.sp
                )
            }
        }
    }
}

@Preview
@Composable
private fun MemeStyleEditBarPreview() {
    MastermemeTheme {
        Surface {
            MemeStyleEditBar(
                onFontSelection = {

                }
            )
        }
    }
}