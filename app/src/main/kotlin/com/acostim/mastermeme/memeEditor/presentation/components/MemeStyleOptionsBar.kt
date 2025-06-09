package com.acostim.mastermeme.memeEditor.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyItemScope
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
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
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.acostim.mastermeme.memeEditor.presentation.state.MemeFont
import com.acostim.mastermeme.memeEditor.presentation.state.colors
import com.acostim.mastermeme.memeEditor.presentation.state.fonts
import com.acostim.mastermeme.ui.theme.MastermemeTheme
import com.acostim.mastermeme.ui.theme.SurfaceContainer

@Composable
fun MemeStyleEditBar(
    modifier: Modifier = Modifier,
    onFontSelection: (MemeFont) -> Unit,
    onColorSelection: (Color) -> Unit
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

        if (isTextSizeOptionsVisible) {

        }

        if (isTextColorOptionsVisible) {
            ChangeColorStyleOptions(
                colors = colors,
                onColorSelection = { color ->
                    onColorSelection(color)
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
    StyleSelectionContainer(fonts) {
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

@Composable
private fun ChangeColorStyleOptions(
    colors: List<Color>,
    onColorSelection: (Color) -> Unit,
) {
    StyleSelectionContainer(colors) {
        Box(
            Modifier
                .size(32.dp)
                .clip(CircleShape)
                .background(it)
                .clickable {
                    onColorSelection(it)
                }
        )
    }
}

@Composable
private fun <T> StyleSelectionContainer(
    items: List<T>,
    itemContent: @Composable (LazyItemScope.(item: T) -> Unit)
) {
    LazyRow(
        modifier = Modifier
            .fillMaxWidth()
            .height(80.dp)
            .padding(
                top = 8.dp,
                bottom = 8.dp,
                start = 12.dp,
                end = 12.dp
            ),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        items(items) {
            itemContent(it)
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

                },
                onColorSelection = {

                }
            )
        }
    }
}