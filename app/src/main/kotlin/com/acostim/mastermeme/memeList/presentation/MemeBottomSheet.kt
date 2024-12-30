package com.acostim.mastermeme.memeList.presentation

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.staggeredgrid.LazyVerticalStaggeredGrid
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridCells
import androidx.compose.foundation.lazy.staggeredgrid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.SheetState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.acostim.mastermeme.R
import com.acostim.mastermeme.ui.loadBitmapFromResources

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MemeBottomSheet(
    sheetState: SheetState,
    showBottomSheet: (Boolean) -> Unit,
    memeTemplates: List<String>,
    onCardClick: (String) -> Unit
) {
    ModalBottomSheet(
        modifier = Modifier
            .fillMaxSize(),
        sheetState = sheetState,
        onDismissRequest = {
            showBottomSheet(false)
        }
    ) {
        ModalSheetContent(
            memeTemplates,
            onCardClick
        )
    }
}

@Composable
private fun ModalSheetContent(
    memeTemplates: List<String>,
    onCardClick: (String) -> Unit
) {
    Text(
        stringResource(R.string.choose_template_title),
        modifier = Modifier.padding(horizontal = 16.dp)
    )

    Text(stringResource(R.string.choose_template_content), modifier = Modifier.padding(16.dp))

    LazyVerticalStaggeredGrid(
        columns = StaggeredGridCells.Fixed(2),
        horizontalArrangement = Arrangement.spacedBy(16.dp),
        verticalItemSpacing = 16.dp,
        contentPadding = PaddingValues(16.dp)
    ) {
        items(memeTemplates) { path ->
            Card(
                shape = RoundedCornerShape(8.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .aspectRatio(1f),
                onClick = {
                    onCardClick(path)
                }
            ) {
                val bitmap = loadBitmapFromResources(LocalContext.current, path)

                bitmap?.let { bitmap ->
                    Image(
                        bitmap = bitmap.asImageBitmap(),
                        contentDescription = null,
                        contentScale = ContentScale.Crop,
                        modifier = Modifier.fillMaxSize()
                    )
                }
            }
        }
    }
}