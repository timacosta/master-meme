package com.acostim.mastermeme.memeList

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.staggeredgrid.LazyVerticalStaggeredGrid
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridCells
import androidx.compose.foundation.lazy.staggeredgrid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.acostim.mastermeme.R
import com.acostim.mastermeme.memeList.state.MemeListUi
import com.acostim.mastermeme.ui.theme.Background
import com.acostim.mastermeme.ui.theme.MastermemeTheme

@Composable
fun MemeListScreen(
    modifier: Modifier = Modifier,
    memes: List<MemeListUi>
) {
    Box(modifier.fillMaxSize().background(Background)) {
        if (memes.isEmpty()) {
            EmptyMemeScreen(modifier = Modifier.align(Alignment.Center))
        } else {
            LazyVerticalStaggeredGrid(
                columns = StaggeredGridCells.Fixed(2),
                horizontalArrangement = Arrangement.spacedBy(16.dp),
                verticalItemSpacing = 16.dp,
                contentPadding = PaddingValues(16.dp)
            ) {
                items(memes) { meme ->
                    Card(
                        shape = RoundedCornerShape(8.dp),
                        modifier = Modifier
                            .fillMaxWidth()
                            .aspectRatio(1f),
                        onClick = {

                        }
                    ) {
                        meme.bitmap?.let { bitmap ->
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
    }
}

@Composable
fun EmptyMemeScreen(
    modifier: Modifier = Modifier,
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier,
    ) {
        Image(
            painter = painterResource(R.drawable.ic_main),
            contentDescription = null,
        )

        Spacer(Modifier.height(8.dp))

        Text(
            stringResource(R.string.empty_memes)
        )
    }
}

@Preview(showBackground = true)
@Composable
fun HomeScreenPreview() {
    MastermemeTheme {
        MemeListScreen(
            memes = emptyList()
        )
    }
}