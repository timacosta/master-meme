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
import androidx.compose.ui.res.stringResource
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.acostim.mastermeme.R
import com.acostim.mastermeme.ui.theme.Surface
import org.koin.androidx.compose.koinViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MemeListRoute(
    navigateToCreateMeme: (String) -> Unit,
    viewModel: MemeListViewModel = koinViewModel(),
) {

    val memeTemplates by viewModel.memeTemplates.collectAsStateWithLifecycle()
    val savedMemes by viewModel.savedMemes.collectAsStateWithLifecycle()
    val sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = false)
    var showBottomSheet by remember { mutableStateOf(false) }

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = stringResource(R.string.memes_list_topbar),
                        color = Color.White
                    )
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Surface
                )
            )
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
            memes = savedMemes
        )

        if (showBottomSheet) {
            MemeBottomSheet(
                sheetState = sheetState,
                memeTemplates = memeTemplates,
                showBottomSheet = {
                    showBottomSheet = it
                },
                onCardClick = navigateToCreateMeme
            )
        }
    }
}