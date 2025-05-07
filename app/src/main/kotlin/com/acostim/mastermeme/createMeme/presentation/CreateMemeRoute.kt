package com.acostim.mastermeme.createMeme.presentation

import android.graphics.Bitmap
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
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
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.acostim.mastermeme.R
import com.acostim.mastermeme.ui.loadBitmapFromResources
import com.acostim.mastermeme.ui.theme.PrimaryContainer
import org.koin.androidx.compose.koinViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CreateMemeRoute(
    path: String,
    onNavigateUp: () -> Unit,
    viewModel: CreateMemeViewModel = koinViewModel()
) {

    var bitmap: Bitmap? by remember { mutableStateOf(null) }
    val context = LocalContext.current

    val state by viewModel.state.collectAsStateWithLifecycle()

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
            memeDecors = state.memeDecors,
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
    ) {
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
                Text(stringResource(R.string.add_text_button))
            }

            Button(
                onClick = {
                }
            ) {
                Text(stringResource(R.string.save_meme_button))
            }
        }
    }
}