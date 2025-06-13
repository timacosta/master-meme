package com.acostim.mastermeme.memeEditor.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.BasicAlertDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.acostim.mastermeme.ui.theme.OnDarkSurfaceContainer

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ExitConfirmationDialog(
    onDismiss: () -> Unit,
    onConfirm: () -> Unit
) {
    BasicAlertDialog(
        modifier = Modifier.background(OnDarkSurfaceContainer),
        onDismissRequest = {
            onDismiss()
        }
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            Text(
                text = "Leave editor?",
                style = MaterialTheme.typography.headlineMedium,
                color = Color.White
            )

            Spacer(Modifier.height(12.dp))

            Text(
                text = "You will lose your precious meme. If you'refine with that, press 'Leave",
                color = Color.White
            )

            Row {
                Spacer(Modifier.weight(1f))
                TextButton(
                    onClick = {
                        onDismiss()
                    }
                ) {
                    Text("Cancel")
                }

                TextButton(
                    onClick = {
                       onConfirm()
                    }
                ) {
                    Text(text = "Leave")
                }
            }
        }
    }
}