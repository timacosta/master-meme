package com.acostim.mastermeme.memeList.presentation

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.acostim.mastermeme.R
import com.acostim.mastermeme.ui.theme.MastermemeTheme

@Composable
fun MemeListScreen(modifier: Modifier = Modifier) {
    Box(modifier.fillMaxSize()) {
        EmptyMemeScreen(modifier = Modifier.align(Alignment.Center))
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

        Text(stringResource(R.string.empty_memes))
    }
}

@Preview(showBackground = true)
@Composable
fun HomeScreenPreview() {
    MastermemeTheme {
        MemeListScreen()
    }
}