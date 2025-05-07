package com.acostim.mastermeme.createMeme.presentation

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Icon
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import com.acostim.mastermeme.core.presentation.UiText

@Composable
fun EditableTextField(
    memeDecor: MemeDecor,
    parentWidth: Int,
    parentHeight: Int,
    onValueChange: (String, String) -> Unit,
    onDrag: (IntOffset) -> Unit,
) {
    val iconSize = 24.dp
    val offsetInPx = LocalDensity.current.run { (iconSize / 2).roundToPx() }

    var textFieldSize by remember { mutableStateOf(IntSize.Zero) }
    var accumulatedOffset by remember { mutableStateOf(memeDecor.offset) }
    var isFocused by remember { mutableStateOf(false) }

    Box(
        modifier = Modifier.padding(iconSize / 2)
    ) {
        Box(
            Modifier
                .offset { memeDecor.offset }
                .pointerInput(Unit) {
                    detectDragGestures(
                        onDrag = { change, dragAmount ->
                            change.consume()

                            val newOffsetX = (accumulatedOffset.x + dragAmount.x)
                                .coerceIn(0f, (parentWidth - textFieldSize.width).toFloat())
                            val newOffsetY = (accumulatedOffset.y + dragAmount.y)
                                .coerceIn(0f, (parentHeight - textFieldSize.height).toFloat())

                            accumulatedOffset = IntOffset(newOffsetX.toInt(), newOffsetY.toInt())

                            onDrag(
                                IntOffset(
                                    newOffsetX.toInt(),
                                    newOffsetY.toInt()
                                )
                            )
                        }
                    )
                }
                .onSizeChanged { size ->
                    textFieldSize = size
                }
        ) {
            TextField(
                modifier = Modifier
                    .border(width = 1.dp, color = Color.White)
                    .onFocusChanged { focusState ->
                        isFocused = focusState.isFocused
                    },
                value = memeDecor.text.asString(),
                onValueChange = {
                    onValueChange(memeDecor.id, it)
                },
                textStyle = TextStyle(fontFamily = memeDecor.fontFamily, color = Color.White),
                colors = TextFieldDefaults.colors(
                    focusedTextColor = Color.Transparent,
                    unfocusedTextColor = Color.Transparent,
                    focusedContainerColor = Color.Transparent,
                    unfocusedContainerColor = Color.Transparent,
                    disabledContainerColor = Color.Transparent,
                    errorContainerColor = Color.Transparent,
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent,
                ),
            )

            if (isFocused) {
                Box(
                    modifier = Modifier
                        .offset {
                            IntOffset(x = +offsetInPx, y = -offsetInPx)
                        }
                        .clip(CircleShape)
                        .size(iconSize)
                        .background(Color.Red)
                        .align(Alignment.TopEnd)
                ) {
                    Icon(
                        imageVector = Icons.Default.Close,
                        tint = Color.White,
                        contentDescription = null,
                        modifier = Modifier.align(Alignment.Center)
                    )
                }
            }
        }
    }
}

@Preview
@Composable
private fun EditableTextFieldPreview() {
    EditableTextField(
        memeDecor = MemeDecor(text = UiText.DynamicString("Write something")),
        parentWidth = 100,
        parentHeight = 100,
        onValueChange = { string1, string2 ->

        },
        onDrag = {

        }
    )
}