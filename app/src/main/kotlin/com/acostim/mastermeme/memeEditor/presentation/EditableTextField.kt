package com.acostim.mastermeme.memeEditor.presentation

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
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
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.dp
import com.acostim.mastermeme.core.presentation.UiText

@Composable
fun EditableTextField(
    memeDecor: MemeDecor,
    parentWidth: Int,
    parentHeight: Int,
    onDrag: (IntOffset) -> Unit,
    onRemove: () -> Unit
) {
    val iconSize = 20.dp
    val offsetInPx = LocalDensity.current.run { (iconSize / 2).roundToPx() }

    var textFieldSize by remember { mutableStateOf(IntSize.Zero) }
    var accumulatedOffset by remember { mutableStateOf(memeDecor.offset) }

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
            Text(
                modifier = Modifier
                    .clip(RoundedCornerShape(2.dp))
                    .border(width = 1.dp, color = Color.White)
                    .padding(8.dp),
                text = memeDecor.text.asString(),
                style = TextStyle(fontFamily = memeDecor.fontFamily, color = Color.White)
            )

            IconButton(
                modifier = Modifier
                    .offset {
                        IntOffset(x = +offsetInPx, y = -offsetInPx)
                    }
                    .clip(CircleShape)
                    .size(iconSize)
                    .background(Color.Red)
                    .align(Alignment.TopEnd),
                onClick = {
                    onRemove()
                }
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

@Preview
@Composable
private fun EditableTextFieldPreview() {
    EditableTextField(
        memeDecor = MemeDecor(text = UiText.DynamicString("Write something")),
        parentWidth = 100,
        parentHeight = 100,
        onDrag = {

        },
        onRemove = {

        }
    )
}