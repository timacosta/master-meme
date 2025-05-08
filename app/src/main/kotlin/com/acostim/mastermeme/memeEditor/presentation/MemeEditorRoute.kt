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
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
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
import com.acostim.mastermeme.ui.theme.OnDarkSurfaceContainer
import org.koin.androidx.compose.koinViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MemeEditorRoute(
    path: String,
    onNavigateUp: () -> Unit,
    viewModel: MemeEditorViewModel = koinViewModel()
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
        MemeEditorScreen(
            bitmap = bitmap,
            modifier = Modifier.padding(innerPadding),
            memeDecors = state.memeDecors,
            onAddMemeDecor = {
                viewModel.onAction(
                    MemeEditorAction.AddMemeDecor(
                        memeDecor = MemeDecor(
                            offset = IntOffset(x = 0, y = 0)
                        )
                    )
                )
            },
            onRemoveMemeDecor = { id ->
                viewModel.onAction(
                    MemeEditorAction.RemoveMemeDecor(id)
                )
            },
            updateMemeDecorOffset = { id, newOffset ->
                viewModel.onAction(
                    MemeEditorAction.UpdateMemeDecor(
                        memeDecorId = id,
                        newOffset = newOffset
                    )
                )
            }
        )
    }
}

@Composable
fun MemeEditorScreen(
    bitmap: Bitmap?,
    modifier: Modifier = Modifier,
    memeDecors: List<MemeDecor>,
    onAddMemeDecor: () -> Unit,
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
                        EditableTextField(
                            memeDecor = memeDecor,
                            parentWidth = imageWidth,
                            parentHeight = imageHeight,
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