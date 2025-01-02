package com.acostim.mastermeme.createMeme.presentation

import android.graphics.Bitmap
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.dp
import com.acostim.mastermeme.R
import com.acostim.mastermeme.ui.loadBitmapFromResources
import com.acostim.mastermeme.ui.theme.Impact
import com.acostim.mastermeme.ui.theme.PrimaryContainer
import org.koin.androidx.compose.koinViewModel
import java.util.UUID

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CreateMemeRoute(
    path: String,
    onNavigateUp: () -> Unit,
    viewModel: CreateMemeViewModel = koinViewModel()
) {

    var bitmap: Bitmap? by remember { mutableStateOf(null) }
    val context = LocalContext.current

    LaunchedEffect(path) {
        bitmap = loadBitmapFromResources(context, path)
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(stringResource(R.string.create_memes_topbar))
                },
                navigationIcon = {
                    IconButton(onClick = onNavigateUp) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = null
                        )
                    }
                }
            )
        },
    ) { innerPadding ->
        CreateMemeScreen(
            bitmap = bitmap,
            modifier = Modifier.padding(innerPadding),
            memeDecors = viewModel.memeDecorItems,
            addMemeDecor = {
                viewModel.addMemeDecor(
                    MemeDecor(
                        offset = IntOffset(x = 0, y = 0)
                    )
                )
            },
            onValueChange = { id, newText ->
                viewModel.onValueChange(id, newText)
            },
            updateMemeDecorOffset = { id, newOffset ->
                viewModel.updateMemeDecorOffset(
                    id,
                    newOffset
                )
            }
        )
    }
}

@Composable
fun CreateMemeScreen(
    bitmap: Bitmap?,
    modifier: Modifier = Modifier,
    memeDecors: List<MemeDecor>,
    onValueChange: (String, String) -> Unit,
    addMemeDecor: () -> Unit,
    updateMemeDecorOffset: (String, IntOffset) -> Unit,
) {

    Box(modifier.fillMaxSize()) {
        Column(
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.align(Alignment.Center)
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
                        EditableTextField(
                            memeDecor = memeDecor,
                            parentWidth = imageWidth,
                            parentHeight = imageHeight,
                            onValueChange = onValueChange,
                            onDrag = { newOffset ->
                                updateMemeDecorOffset(
                                    memeDecor.id,
                                    newOffset
                                )
                            }
                        )
                    }
                }
            }
        }

        Row(
            horizontalArrangement = Arrangement.SpaceEvenly,
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.BottomCenter)
                .padding(16.dp)
        ) {
            OutlinedButton(
                border = BorderStroke(1.dp, PrimaryContainer),
                shape = RoundedCornerShape(10),
                onClick = {
                    addMemeDecor()
                }
            ) {
                Text("Add text")
            }

            Button(
                onClick = {
                }
            ) {
                Text("Save meme")
            }
        }


    }
}

@Composable
private fun EditableTextField(
    memeDecor: MemeDecor,
    parentWidth: Int,
    parentHeight: Int,
    onValueChange: (String, String) -> Unit,
    onDrag: (IntOffset) -> Unit,
) {
    var textFieldSize by remember { mutableStateOf(IntSize.Zero) }
    var accumulatedOffset by remember { mutableStateOf(memeDecor.offset) }

    Box(
        Modifier
            .offset { memeDecor.offset }
            .clip(RoundedCornerShape(2.dp))
            .border(width = 1.dp, Color.White)
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
            value = memeDecor.text,
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
    }
}

data class MemeDecor(
    val id: String = UUID.randomUUID().toString(),
    val text: String = "Tap twice to edit",
    val offset: IntOffset,
    val fontFamily: FontFamily = Impact
)