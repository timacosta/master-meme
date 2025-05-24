package com.acostim.mastermeme.memeEditor.presentation

import android.graphics.Bitmap
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import com.acostim.mastermeme.memeEditor.presentation.components.MemeDecorField
import com.acostim.mastermeme.memeEditor.presentation.components.MemeEditorBottomBar
import com.acostim.mastermeme.ui.loadBitmapFromResources
import com.acostim.mastermeme.ui.theme.OnDarkSurfaceContainer

@Composable
fun MemeEditorScreen(
    bitmap: Bitmap?,
    modifier: Modifier = Modifier,
    memeDecors: List<MemeDecor>,
    onAddMemeDecor: () -> Unit,
    onClickMemeDecor: (id: String) -> Unit,
    onRemoveMemeDecor: (id: String) -> Unit,
    updateMemeDecorOffset: (String, IntOffset) -> Unit,
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
                Box {

                    var imageWidth by remember { mutableIntStateOf(0) }
                    var imageHeight by remember { mutableIntStateOf(0) }

                    Image(
                        modifier = Modifier
                            .size(380.dp)
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
                            onClick = { id ->
                                onClickMemeDecor(id)
                            },
                            onDrag = { newOffset ->
                                updateMemeDecorOffset(
                                    memeDecor.id,
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

        MemeEditorBottomBar(
            modifier = Modifier.align(Alignment.BottomCenter),
            onUndo = {},
            onRedo = {},
            onAddMemeDecor = {
                onAddMemeDecor()
            },
            onSaveMeme = {}
        )
    }
}

@Preview
@Composable
private fun MemeEditorScreenPreview() {
    val bitmap = loadBitmapFromResources(
        context = LocalContext.current,
        assetPath = "ajtl_46.webp"
    )

    MemeEditorScreen(
        bitmap = bitmap,
        memeDecors = emptyList(),
        onAddMemeDecor = {},
        onRemoveMemeDecor = {},
        onClickMemeDecor = {},
        updateMemeDecorOffset = { _, _ ->

        },
    )
}