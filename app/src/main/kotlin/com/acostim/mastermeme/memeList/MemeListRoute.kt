package com.acostim.mastermeme.memeList

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.acostim.mastermeme.R
import com.acostim.mastermeme.memeList.components.SelectTopBar
import com.acostim.mastermeme.memeList.components.SortDropdownMenu
import com.acostim.mastermeme.memeList.components.SortTopBar
import com.acostim.mastermeme.ui.theme.Surface
import org.koin.androidx.compose.koinViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MemeListRoute(
    navigateToCreateMeme: (String) -> Unit,
    viewModel: MemeListViewModel = koinViewModel(),
) {
    val context = LocalContext.current
    val state by viewModel.state.collectAsStateWithLifecycle()
    val sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = false)
    var showBottomSheet by remember { mutableStateOf(false) }

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            if (state.isSelectedMode) {
                SelectTopBar(
                    selectedCount = state.savedMemes.filter { it.isSelected }.size,
                    onCancel = {
                        viewModel.onAction(MemeListAction.CancelSelection)
                    },
                    onShare = {
                        viewModel.onAction(MemeListAction.ShareSelectedMemes(context))
                    },
                    onDelete = {
                        viewModel.onAction(MemeListAction.OnDelete(state.savedMemes.filter { it.isSelected }))
                    }
                )
            } else {
                SortTopBar(
                    expanded = state.isSortOptionsVisible,
                    selectedSortOption = state.selectedSortOption,
                    onExpand = {
                        viewModel.onAction(MemeListAction.ToggleSortOptionsVisibility(state.isSortOptionsVisible))
                    },
                    onSelect = { sortOption ->
                        viewModel.onAction(MemeListAction.ToggleSortOption(sortOption))
                    }

                )
            }
        },
        floatingActionButton = {
            FloatingActionButton(onClick = {
                viewModel.onAction(MemeListAction.LoadMemeTemplates)
                showBottomSheet = true
            }) {
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = null
                )
            }
        }
    ) { innerPadding ->

        MemeListScreen(
            modifier = Modifier.padding(innerPadding),
            memes = state.savedMemes,
            onFavoriteClick = { meme ->
                viewModel.onAction(
                    MemeListAction.OnFavoriteClick(
                        id = meme.uid,
                        isFavorite = !meme.isFavorite
                    )
                )
            },
            onLongPress = { meme ->
                viewModel.onAction(MemeListAction.OnLongPress(meme))
            },
            isSelectedMode = state.isSelectedMode,
            onSelectedMeme = { meme ->
                viewModel.onAction(MemeListAction.OnSelectedMeme(meme))
            }
        )

        if (showBottomSheet) {
            MemeBottomSheet(
                sheetState = sheetState,
                memeTemplates = state.templatesPathList,
                showBottomSheet = {
                    showBottomSheet = it
                },
                onCardClick = navigateToCreateMeme
            )
        }
    }
}

