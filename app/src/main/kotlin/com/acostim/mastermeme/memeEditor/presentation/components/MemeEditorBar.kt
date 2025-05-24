package com.acostim.mastermeme.memeEditor.presentation.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
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
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.PreviewScreenSizes
import androidx.compose.ui.unit.dp
import com.acostim.mastermeme.R
import com.acostim.mastermeme.ui.theme.MastermemeTheme
import com.acostim.mastermeme.ui.theme.OnPrimaryFixed
import com.acostim.mastermeme.ui.theme.PrimaryContainer
import com.acostim.mastermeme.ui.theme.SurfaceContainer

@Composable
fun MemeEditorBottomBar(
    modifier: Modifier = Modifier,
    onUndo: () -> Unit,
    onRedo: () -> Unit,
    onAddMemeDecor: () -> Unit,
    onSaveMeme: () -> Unit
) {
    BoxWithConstraints(
        modifier = modifier
            .fillMaxWidth()
            .background(SurfaceContainer)
    ) {
        val isCompact = maxWidth < 360.dp

        Row(
            modifier = Modifier.fillMaxWidth().padding(vertical = 16.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Row {
                IconButton(onClick = onUndo) {
                    Icon(painterResource(R.drawable.ic_undo), contentDescription = null, tint = Color.Unspecified)
                }
                IconButton(onClick = onRedo) {
                    Icon(painterResource(R.drawable.ic_redo), contentDescription = null, tint = Color.Unspecified)
                }
            }

            if (isCompact) {
                Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                    AddTextButton(onAddMemeDecor)
                    SaveButton(onSaveMeme)
                }
            } else {
                Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                    AddTextButton(onAddMemeDecor)
                    SaveButton(onSaveMeme)
                }
            }

            Spacer(Modifier.width(8.dp))
        }
    }
}

@Composable
private fun AddTextButton(onClick: () -> Unit) {
    OutlinedButton(
        border = BorderStroke(1.dp, PrimaryContainer),
        shape = RoundedCornerShape(10.dp),
        onClick = onClick
    ) {
        Text(text = stringResource(R.string.add_text_button), fontWeight = FontWeight.Bold)
    }
}

@Composable
private fun SaveButton(onClick: () -> Unit) {
    OutlinedButton(
        border = BorderStroke(1.dp, PrimaryContainer),
        shape = RoundedCornerShape(10.dp),
        colors = ButtonDefaults.outlinedButtonColors(containerColor = PrimaryContainer),
        onClick = onClick
    ) {
        Text(
            text = stringResource(R.string.save_meme_button),
            fontWeight = FontWeight.Bold,
            color = OnPrimaryFixed
        )
    }
}

@PreviewScreenSizes
@Composable
private fun MemeEditorBottomBarPreview() {
    MastermemeTheme {
        Surface {
            MemeEditorBottomBar(
                onUndo = {},
                onRedo = {},
                onAddMemeDecor = {},
                onSaveMeme = {}
            )
        }
    }
}