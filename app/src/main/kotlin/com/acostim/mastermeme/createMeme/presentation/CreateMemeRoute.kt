package com.acostim.mastermeme.createMeme.presentation

import android.graphics.Bitmap
import androidx.compose.foundation.Image
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
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
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.acostim.mastermeme.R
import com.acostim.mastermeme.ui.loadBitmapFromResources
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
    addMemeDecor: () -> Unit,
    updateMemeDecorOffset: (String, IntOffset) -> Unit,
) {

    Box(modifier.fillMaxSize()) {
        Column(
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            bitmap?.let { bitmap ->
                Image(
                    modifier = Modifier.size(380.dp),
                    bitmap = bitmap.asImageBitmap(),
                    contentScale = ContentScale.Crop,
                    contentDescription = null
                )
            }
        }

        memeDecors.forEach { memeDecor ->
            EditableTextField(memeDecor = memeDecor, onDrag = { newOffset ->
                updateMemeDecorOffset(memeDecor.id, newOffset)
            })
        }

        TextButton(
            modifier = Modifier.align(Alignment.BottomCenter),
            onClick = {
                addMemeDecor()
            }) {
            Text("Add text")
        }
    }
}

@Composable
private fun EditableTextField(
    memeDecor: MemeDecor,
    onDrag: (IntOffset) -> Unit,
) {
    var value by remember { mutableStateOf("") }

    Box(Modifier.fillMaxSize()) {
        TextField(
            value = value,
            onValueChange = {
                value = it
            },
            modifier = Modifier
                .offset {
                    memeDecor.offset
                }
                .pointerInput(Unit) {
                    detectDragGestures(
                        onDrag = { change, dragAmount ->
                            change.consume()
                            onDrag(
                                IntOffset(
                                    dragAmount.x.toInt(), dragAmount.y.toInt()
                                )
                            )
                        }
                    )
                }
        )
    }
}

data class MemeDecor(
    val id: String = UUID.randomUUID().toString(),
    val offset: IntOffset
)