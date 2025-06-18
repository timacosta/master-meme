package com.acostim.mastermeme.memeEditor.presentation

import androidx.compose.foundation.layout.padding
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
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.IntOffset
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.acostim.mastermeme.R
import com.acostim.mastermeme.core.presentation.ObserveAsEvent
import com.acostim.mastermeme.memeEditor.presentation.components.EditMemeDecorDialog
import com.acostim.mastermeme.memeEditor.presentation.components.ExitConfirmationDialog
import com.acostim.mastermeme.memeEditor.presentation.state.MemeDecor
import com.acostim.mastermeme.memeEditor.presentation.state.MemeEditorAction
import com.acostim.mastermeme.memeEditor.presentation.state.MemeEditorEvent
import com.acostim.mastermeme.ui.loadBitmapFromResources
import org.koin.androidx.compose.koinViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MemeEditorRoute(
    path: String,
    onNavigateUp: () -> Unit,
    viewModel: MemeEditorViewModel = koinViewModel()
) {

    val context = LocalContext.current

    val state by viewModel.state.collectAsStateWithLifecycle()

    LaunchedEffect(path) {
        val bitmap = loadBitmapFromResources(context, path)
        if (bitmap != null) {
            viewModel.onAction(
                MemeEditorAction.StoreBackgroundImage(bitmap)
            )
        }
    }

    ObserveAsEvent(viewModel.events) { event ->
        when (event) {
            is MemeEditorEvent.NavigateBack -> onNavigateUp()
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(stringResource(R.string.create_memes_topbar))
                },
                navigationIcon = {
                    IconButton(onClick = {
                        viewModel.onAction(MemeEditorAction.OnExitDialog)
                    }
                    ) {
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
            bitmap = state.backgroundBitmap,
            modifier = Modifier.padding(innerPadding),
            memeDecors = state.memeDecors,
            selectedMemeDecor = state.selectedMemeDecor,
            isSaveOptionsBarVisible = state.isSavingOptionsVisible,
            isStyleOptionsBarVisible = state.isStylingOptionsVisible,
            onAddMemeDecor = {
                viewModel.onAction(
                    MemeEditorAction.AddMemeDecor(
                        memeDecor = MemeDecor(
                            offset = IntOffset(x = 0, y = 0)
                        )
                    )
                )
            },
            onFocusCleared = {
                viewModel.onAction(MemeEditorAction.OnFocusCleared)
            },
            onRemoveMemeDecor = { memeDecor ->
                viewModel.onAction(
                    MemeEditorAction.RemoveMemeDecor(memeDecor)
                )
            },
            onOpenStylingOptions = { memeDecor ->
                viewModel.onAction(
                    MemeEditorAction.OpenStylingOptions(memeDecor)
                )
            },
            onUpdateMemeDecorText = { memeDecor ->
                viewModel.onAction(MemeEditorAction.OpenEditDialog(memeDecor))
            },
            updateMemeDecorOffset = { memeDecor, newOffset ->
                viewModel.onAction(
                    MemeEditorAction.UpdateMemeDecorOffset(
                        memeDecor = memeDecor,
                        newOffset = newOffset
                    )
                )
            },
            onFontSelection = { memeFont ->
                viewModel.onAction(
                    MemeEditorAction.UpdateMemeDecorFont(memeFont)
                )
            },
            onColorSelection = { color ->
                viewModel.onAction(
                    MemeEditorAction.UpdateMemeDecorColor(color)
                )
            },
            onSizeSelection = { size ->
                viewModel.onAction(
                    MemeEditorAction.UpdateMemeDecorSize(size)
                )
            },
            undo = {
                viewModel.onAction(MemeEditorAction.Undo)
            },
            redo = {
                viewModel.onAction(MemeEditorAction.Redo)
            },
            onDiscardChanges = {
                viewModel.onAction(MemeEditorAction.DiscardLatestChange)
            },
            onConfirmChanges = {
                viewModel.onAction(MemeEditorAction.OnFocusCleared)
            },
            onSaveMeme = { graphicsLayer ->
                viewModel.onAction(MemeEditorAction.SaveMeme(graphicsLayer))
            }
        )
    }

    if (state.isInEditMode) {
        EditMemeDecorDialog(
            value = viewModel.getCurrentMemeDecorText(state.selectedMemeDecor).asString(),
            onDismiss = {
                viewModel.onAction(MemeEditorAction.CloseEditDialog)
            },
            onConfirm = {
                viewModel.onAction(MemeEditorAction.UpdateText(it))
            }
        )
    }

    if (state.isExitDialogShown) {
        ExitConfirmationDialog(
            onDismiss = {
                viewModel.onAction(MemeEditorAction.OnDismissExitDialog)
            },
            onConfirm = {
                viewModel.onAction(MemeEditorAction.OnDismissExitDialog)
                viewModel.onAction(MemeEditorAction.OnConfirmExitDialog)
            }
        )
    }
}
