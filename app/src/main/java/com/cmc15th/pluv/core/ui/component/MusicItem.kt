package com.cmc15th.pluv.core.ui.component

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.cmc15th.pluv.core.designsystem.component.PlaylistCheckBox
import com.cmc15th.pluv.core.designsystem.theme.Content1
import com.cmc15th.pluv.core.designsystem.theme.Content2
import com.cmc15th.pluv.core.designsystem.theme.SelectAllContent
import com.cmc15th.pluv.core.designsystem.theme.Title5

@Composable
fun MusicItem(
    isChecked: Boolean = true,
    thumbNailContent: @Composable () -> Unit = {},
    modifier: Modifier = Modifier,
    musicName: String,
    artistName: String,
    onCheckedChange: (Boolean) -> Unit = {}
) {

    val backgroundColor =
        if (isChecked) Color(0xFFCB84FF).copy(alpha = 0.08f) else MaterialTheme.colorScheme.surface

    Row(
        modifier = modifier
            .fillMaxWidth()
            .background(color = backgroundColor)
            .clickable { onCheckedChange(!isChecked) }
            .padding(vertical = 10.dp, horizontal = 24.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            thumbNailContent()
            Spacer(modifier = Modifier.size(16.dp))

            Column {
                Text(text = musicName, style = Title5)
                Spacer(modifier = Modifier.size(6.dp))
                Text(text = artistName, style = Content1)
            }
        }

        if (isChecked) {
            PlaylistCheckBox(
                modifier = Modifier.size(16.dp)
            )
        }
    }
}

@Composable
fun MusicsHeader(
    modifier: Modifier = Modifier,
    selectedMusicCount: Int,
    isSelectedAll: Boolean,
    onAllSelectedClick: (Boolean) -> Unit = {}

) {
    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            text = "${selectedMusicCount}곡",
            style = Content2
        )

        AllSelectedText(
            modifier = Modifier.clickable { onAllSelectedClick(isSelectedAll) },
            isSelectedAll = isSelectedAll
        )
    }
}

@Composable
fun AllSelectedText(
    modifier: Modifier = Modifier,
    isSelectedAll: Boolean
) {

    if (isSelectedAll) {
        Row(
            modifier = modifier
        ) {
            PlaylistCheckBox(
                modifier = Modifier.size(16.dp)
            )
            Spacer(modifier = Modifier.size(6.dp))
            Text(text = "전체선택", style = SelectAllContent)
        }
    } else {
        Text(
            modifier = modifier,
            text = "전체선택",
            style = Content2
        )
    }
}

@Preview
@Composable
fun MusicItemPreview() {
    MusicItem(
        musicName = "Dynamite",
        artistName = "BTS",
        onCheckedChange = {}
    )
}

@Preview
@Composable
fun MusicHeaderPreview() {
    MusicsHeader(
        modifier = Modifier.fillMaxWidth(),
        selectedMusicCount = 5, isSelectedAll = false
    )
}