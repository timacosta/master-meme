package com.acostim.mastermeme.memeEditor.presentation.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
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
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Row(
            modifier = Modifier.clickable {
                onSaveToDevice()
            },
            horizontalArrangement = Arrangement.spacedBy(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                painter = painterResource(R.drawable.ic_download),
                tint = Color.Unspecified,
                contentDescription = null
            )

            Column {
                Text(
                    text = "Save to device",
                    fontWeight = FontWeight.Bold,
                    color = SecondaryFixedDim
                )

                Spacer(Modifier.height(8.dp))

                Text(
                    "Save created meme in the Files of your device",
                    color = Color.White
                )
            }
        }

        Row(
            modifier = Modifier.clickable {
                onShareMeme()
            },
            horizontalArrangement = Arrangement.spacedBy(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                painter = painterResource(R.drawable.ic_share),
                contentDescription = null,
                tint = SecondaryFixedDim
            )

            Column {
                Text(
                    text = "Share the meme",
                    fontWeight = FontWeight.Bold,
                    color = SecondaryFixedDim
                )

                Spacer(Modifier.height(8.dp))

                Text(
                    text = "Share your meme or open it in the other App",
                )
            }
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