package com.acostim.mastermeme.memeEditor.presentation.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.displayCutout
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.acostim.mastermeme.R
import com.acostim.mastermeme.ui.theme.MastermemeTheme
import com.acostim.mastermeme.ui.theme.SecondaryFixedDim

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MemeSaveBottomSheet(
    modifier: Modifier = Modifier,
    onDismissRequest: () -> Unit,
    onSaveToDevice: () -> Unit,
    onShareMeme: () -> Unit
) {
    ModalBottomSheet(
        modifier = modifier,
        onDismissRequest = {
            onDismissRequest()
        }
    ) {
        MemeSaveContent(
            onSaveToDevice = {
                onSaveToDevice()
            },
            onShareMeme = {
                onShareMeme()
            }
        )
    }
}

@Composable
private fun MemeSaveContent(
    onSaveToDevice: () -> Unit,
    onShareMeme: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 12.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(20.dp)
    ) {
        MemeOptionRow(
            iconRes = R.drawable.ic_download,
            title = "Save to device",
            subtitle = "Save created meme in the Files of your device",
            onClick = onSaveToDevice
        )

        MemeOptionRow(
            iconRes = R.drawable.ic_share,
            title = "Share the meme",
            subtitle = "Share your meme or open it in the other App",
            onClick = onShareMeme
        )
    }
}

@Composable
private fun MemeOptionRow(
    iconRes: Int,
    title: String,
    subtitle: String,
    onClick: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() },
        horizontalArrangement = Arrangement.spacedBy(16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            painter = painterResource(iconRes),
            contentDescription = null,
            tint = SecondaryFixedDim
        )

        Column {
            Text(
                text = title,
                style = TextStyle(
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Medium,
                    color = SecondaryFixedDim
                )
            )

            Spacer(Modifier.height(4.dp))

            Text(
                text = subtitle,
                style = TextStyle(
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Light,
                    color = Color.White
                )
            )
        }
    }
}

@Preview
@Composable
private fun MemeSaveBottomSheetPreview() {
    MastermemeTheme {
        MemeSaveContent(
            onSaveToDevice = {

            },
            onShareMeme = {

            }
        )
    }
}