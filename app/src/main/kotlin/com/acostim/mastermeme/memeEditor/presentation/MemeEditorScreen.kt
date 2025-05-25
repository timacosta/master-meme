package com.acostim.mastermeme.memeEditor.presentation

import android.graphics.Bitmap
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.core.graphics.createBitmap
import com.acostim.mastermeme.memeEditor.presentation.components.MemeDecorField
import com.acostim.mastermeme.memeEditor.presentation.components.MemeSaveOptionsBar
import com.acostim.mastermeme.memeEditor.presentation.components.MemeStyleEditBar
import com.acostim.mastermeme.ui.theme.MastermemeTheme
import com.acostim.mastermeme.ui.theme.OnDarkSurfaceContainer

@Composable
fun MemeEditorScreen(
    bitmap: Bitmap?,
    modifier: Modifier = Modifier,
    memeDecors: List<MemeDecor>,
    selectedMemeDecor: MemeDecor?,
    isSaveOptionsBarVisible: Boolean,
    isStyleOptionsBarVisible: Boolean,
    onAddMemeDecor: () -> Unit,
    onFocusCleared: () -> Unit,
    onOpenStylingOptions: (MemeDecor) -> Unit,
    onUpdateMemeDecorText: (MemeDecor) -> Unit,
    onRemoveMemeDecor: (MemeDecor) -> Unit,
    updateMemeDecorOffset: (MemeDecor, IntOffset) -> Unit,
) {
    val focusManager = LocalFocusManager.current

    Box(
        modifier
            .fillMaxSize()
            .clickable(
                indication = null,
                interactionSource = remember { MutableInteractionSource() }
            ) {
                focusManager.clearFocus()
                focusManager.clearFocus()
                if (selectedMemeDecor != null) {
                    onFocusCleared()
                }
            }
            .background(OnDarkSurfaceContainer)
    ) {
        Column(
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .align(Alignment.Center)
                .padding(8.dp)
        ) {
            bitmap?.let { bitmap ->
                val imageRatio = bitmap.width.toFloat() / bitmap.height.toFloat()

                Box {

                    var imageWidth by remember { mutableIntStateOf(0) }
                    var imageHeight by remember { mutableIntStateOf(0) }

                    Image(
                        modifier = Modifier
                            .fillMaxWidth()
                            .aspectRatio(imageRatio)
                            .onSizeChanged { size ->
                                imageWidth = size.width
                                imageHeight = size.height
                            },
                        bitmap = bitmap.asImageBitmap(),
                        contentScale = ContentScale.Crop,
                        contentDescription = null
                    )

                    memeDecors.forEach { memeDecor ->
                        MemeDecorField(
                            memeDecor = memeDecor,
                            parentWidth = imageWidth,
                            parentHeight = imageHeight,
                            onFocusCleared = {
                                onFocusCleared()
                            },
                            onClick = { memeDecor ->
                                onOpenStylingOptions(memeDecor)
                            },
                            onDoubleClick = { memeDecor ->
                                onUpdateMemeDecorText(memeDecor)
                            },
                            onDrag = { newOffset ->
                                updateMemeDecorOffset(
                                    memeDecor,
                                    newOffset
                                )
                            },
                            onRemove = { id ->
                                onRemoveMemeDecor(id)
                            }
                        )
                    }
                }
            }
        }

        if (isSaveOptionsBarVisible) {
            MemeSaveOptionsBar(
                modifier = Modifier.align(Alignment.BottomCenter),
                onUndo = {},
                onRedo = {},
                onAddMemeDecor = {
                    onAddMemeDecor()
                },
                onSaveMeme = {}
            )
        }

        if (isStyleOptionsBarVisible) {
            MemeStyleEditBar(
                modifier = Modifier.align(Alignment.BottomCenter),
            )
        }
    }
}

@Preview
@Composable
private fun MemeEditorScreenPreview() {
    val bitmap = createBitmap(380, 380)
    val canvas = android.graphics.Canvas(bitmap)
    canvas.drawColor(android.graphics.Color.GRAY)

    MastermemeTheme {
        Surface {
            MemeEditorScreen(
                bitmap = bitmap,
                memeDecors = emptyList(),
                selectedMemeDecor = null,
                isStyleOptionsBarVisible = true,
                isSaveOptionsBarVisible = false,
                onAddMemeDecor = {},
                onRemoveMemeDecor = {},
                onFocusCleared = {},
                onOpenStylingOptions = {},
                onUpdateMemeDecorText = {},
                updateMemeDecorOffset = { _, _ ->

                },
            )
        }
    }
}