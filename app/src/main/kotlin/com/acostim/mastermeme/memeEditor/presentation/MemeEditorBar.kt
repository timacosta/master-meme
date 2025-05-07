package com.acostim.mastermeme.memeEditor.presentation

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.acostim.mastermeme.R
import com.acostim.mastermeme.ui.theme.MastermemeTheme
import com.acostim.mastermeme.ui.theme.OnPrimaryFixed
import com.acostim.mastermeme.ui.theme.PrimaryContainer

@Composable
fun MemeEditorBottomBar(
    modifier: Modifier = Modifier,
    onUndo: () -> Unit,
    onRedo: () -> Unit,
    onAddMemeDecor: () -> Unit,
    onSaveMeme: () -> Unit
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {

        IconButton(
            onClick = {
                onUndo()
            }
        ) {
            Icon(
                painter = painterResource(R.drawable.ic_undo),
                contentDescription = null,
                tint = Color.Unspecified,
            )
        }

        IconButton(
            onClick = {
                onRedo()
            }
        ) {
            Icon(
                painter = painterResource(R.drawable.ic_redo),
                tint = Color.Unspecified,
                contentDescription = null
            )
        }

        Spacer(Modifier.weight(1f))

        OutlinedButton(
            border = BorderStroke(1.dp, PrimaryContainer),
            shape = RoundedCornerShape(10.dp),
            onClick = {
                onAddMemeDecor()
            }
        ) {
            Text(
                text = stringResource(R.string.add_text_button),
                fontWeight = FontWeight.Bold
            )
        }

        Spacer(Modifier.width(8.dp))

        OutlinedButton(
            border = BorderStroke(1.dp, PrimaryContainer),
            shape = RoundedCornerShape(10.dp),
            colors = ButtonDefaults.outlinedButtonColors(
                containerColor = PrimaryContainer
            ),
            onClick = {
                onSaveMeme()
            }
        ) {
            Text(
                text = stringResource(R.string.save_meme_button),
                color = OnPrimaryFixed,
                fontWeight = FontWeight.Bold
            )
        }
    }
}

@Preview
@Composable
private fun MemeEditorBottomBarPreview() {
    MastermemeTheme {
        MemeEditorBottomBar(
            onUndo = {},
            onRedo = {},
            onAddMemeDecor = {},
            onSaveMeme = {}
        )
    }
}