package com.acostim.mastermeme.memeList.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.acostim.mastermeme.R
import com.acostim.mastermeme.memeList.state.SelectedSortOption
import com.acostim.mastermeme.ui.theme.MastermemeTheme

@Composable
fun SortDropdownMenu(
    expanded: Boolean,
    onExpand: () -> Unit,
    selectedSortOption: SelectedSortOption,
    onSelectSortOption: (SelectedSortOption) -> Unit
) {
    Row {
        Text(
            if (selectedSortOption == SelectedSortOption.FAVOURITES) stringResource(R.string.favourites_first) else stringResource(
                R.string.newest_first
            )
        )

        Icon(
            modifier = Modifier.clickable {
                onExpand()
            },
            imageVector = Icons.Default.ArrowDropDown,
            contentDescription = null
        )

        DropdownMenu(
            expanded = expanded,
            onDismissRequest = {
                onExpand()
            },
        ) {
            DropdownMenuItem(
                text = { Text(text = stringResource(R.string.favourites_first)) },
                onClick = {
                    onExpand()
                    onSelectSortOption(SelectedSortOption.FAVOURITES)
                }
            )

            DropdownMenuItem(
                text = { Text(text = stringResource(R.string.newest_first)) },
                onClick = {
                    onExpand()
                    onSelectSortOption(SelectedSortOption.NEWEST)
                }
            )
        }
    }
}

@Preview
@Composable
fun SortDropdownMenuPreview() {
    MastermemeTheme {
        Surface {
            SortDropdownMenu(
                expanded = true,
                selectedSortOption = SelectedSortOption.NEWEST,
                onSelectSortOption = {},
                onExpand = {}
            )
        }
    }
}