package com.acostim.mastermeme.memeList.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Share
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.acostim.mastermeme.R
import com.acostim.mastermeme.memeList.state.SelectedSortOption
import com.acostim.mastermeme.ui.theme.Surface

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SortTopBar(
    expanded: Boolean,
    selectedSortOption: SelectedSortOption,
    onExpand: () -> Unit,
    onSelect: (SelectedSortOption) -> Unit
) {
    TopAppBar(
        title = {
            Text(
                text = stringResource(R.string.memes_list_topbar),
                color = Color.White
            )
        },
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = Surface
        ),
        actions = {
            SortDropdownMenu(
                expanded = expanded,
                selectedSortOption = selectedSortOption,
                onExpand = {
                    onExpand()
                },
                onSelectSortOption = { sortOption ->
                    onSelect(sortOption)
                }
            )
        }
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SelectTopBar(
    selectedCount: Int,
    onCancel: () -> Unit,
    onShare: () -> Unit,
    onDelete: () -> Unit
) {
    TopAppBar(
        title = {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                IconButton(onClick = onCancel) {
                    Icon(
                        painter = painterResource(R.drawable.close),
                        contentDescription = "Cancel",
                    )
                }

                Text(
                    text = "$selectedCount",
                    color = Color.White
                )
            }
        },
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = Surface
        ),
        actions = {
            IconButton(onClick = onShare) {
                Icon(
                    imageVector = Icons.Default.Share,
                    contentDescription = "Share",
                    tint = Color.White
                )
            }
            IconButton(onClick = onDelete) {
                Icon(
                    imageVector = Icons.Default.Delete,
                    contentDescription = "Delete",
                    tint = Color.White
                )
            }
        }
    )
}