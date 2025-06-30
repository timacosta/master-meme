package com.acostim.mastermeme.memeList.components

import android.graphics.Bitmap
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawWithCache
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import com.acostim.mastermeme.R

@Composable
fun MemeItem(
    modifier: Modifier = Modifier,
    bitmap: ImageBitmap,
    isFavorite: Boolean,
    onFavoriteClick: () -> Unit
) {
    Box(
        modifier = modifier
    ) {
        Image(
            modifier = Modifier
                .fillMaxSize()
                .drawWithCache {
                    onDrawWithContent {
                        drawContent()
                        drawRect(
                            brush = Brush.radialGradient(
                                colors = listOf(
                                    Color.Black.copy(alpha = 0.9f),
                                    Color.Transparent,
                                ),
                                center = Offset(size.width, size.height),
                                radius = size.maxDimension / 2f
                            )
                        )
                    }
                },
            bitmap = bitmap,
            contentDescription = null,
            contentScale = ContentScale.Crop,
        )

        IconButton(
            modifier = Modifier
                .size(60.dp)
                .zIndex(1f)
                .align(Alignment.BottomEnd),
            onClick = {
                onFavoriteClick()
            }
        ) {
            Icon(
                modifier = Modifier.size(60.dp),
                painter = painterResource(if (isFavorite) R.drawable.ic_favorite_filled else R.drawable.ic_favorite_empty),
                contentDescription = null
            )
        }
    }
}

@Preview
@Composable
private fun PreviewMemeItem() {
    val bitmap = remember {
        Bitmap.createBitmap(400, 400, Bitmap.Config.ARGB_8888).apply {
            eraseColor(Color.Cyan.toArgb())
        }
    }

    MemeItem(
        modifier = Modifier.size(300.dp, 300.dp),
        bitmap = bitmap.asImageBitmap(),
        isFavorite = false,
        onFavoriteClick = {}
    )
}