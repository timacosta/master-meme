package com.acostim.mastermeme.memeList.components

import android.graphics.Bitmap
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.zIndex
import com.acostim.mastermeme.R

@Composable
fun MemeItem(
    bitmap: ImageBitmap,
    isFavorite: Boolean,
    onFavoriteClick: () -> Unit
) {
    Box {
        Image(
            modifier = Modifier.fillMaxSize(),
            bitmap = bitmap,
            contentDescription = null,
            contentScale = ContentScale.Crop,
        )

        IconButton(
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .zIndex(1f),
            onClick = {
                onFavoriteClick()
            }
        ) {
            Icon(
                painter = painterResource(if (isFavorite) R.drawable.ic_favorite_filled else R.drawable.ic_favorite_empty),
                contentDescription = null
            )
        }
    }
}

@Preview
@Composable
private fun PreviewMemeItem() {
    val bitmap = Bitmap.createBitmap(400, 400, Bitmap.Config.ARGB_8888)
    MemeItem(
        bitmap = bitmap.asImageBitmap(),
        isFavorite = false,
        onFavoriteClick = {

        }
    )
}