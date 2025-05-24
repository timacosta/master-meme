package com.acostim.mastermeme.memeEditor.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.BasicAlertDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.TextFieldDefaults.indicatorLine
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.acostim.mastermeme.R
import com.acostim.mastermeme.ui.theme.MastermemeTheme
import com.acostim.mastermeme.ui.theme.OnDarkSurfaceContainer

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EditMemeDecorDialog(
    value: String,
    onDismiss: () -> Unit,
    onConfirm: (String) -> Unit
) {

    var tempValue by remember { mutableStateOf(value) }

    BasicAlertDialog(
        modifier = Modifier.background(OnDarkSurfaceContainer),
        onDismissRequest = { onDismiss() },
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Text(
                text = stringResource(R.string.dialog_title),
                color = Color.White
            )

            BasicTextField(
                value = tempValue,
                onValueChange = {
                    tempValue = it
                },
                textStyle = LocalTextStyle.current.copy(color = Color.White),
                modifier = Modifier
                    .fillMaxWidth()
                    .indicatorLine(
                        enabled = true,
                        isError = false,
                        interactionSource = remember { MutableInteractionSource() },
                        colors = TextFieldDefaults.colors()
                    ),
            ) { innerTextField ->
                Box(Modifier.padding(bottom = 8.dp)) {
                    innerTextField()
                }
            }

            Row {
                Spacer(Modifier.weight(1f))
                TextButton(
                    onClick = {
                        onDismiss()
                    }
                ) {
                    Text(stringResource(R.string.dialog_cancel_btn))
                }

                TextButton(
                    onClick = {
                        onConfirm(tempValue)
                    }
                ) {
                    Text(text = stringResource(R.string.dialog_ok_btn))
                }
            }
        }
    }
}

@Preview
@Composable
private fun EditMemeDecorDialogPreview() {
    MastermemeTheme {
        Surface {
            EditMemeDecorDialog(
                value = "Tap twice to edit",
                onDismiss = {},
                onConfirm = {}
            )
        }
    }
}