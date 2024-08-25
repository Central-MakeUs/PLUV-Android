package com.cmc15th.pluv.core.ui.component

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.cmc15th.pluv.R
import com.cmc15th.pluv.core.designsystem.theme.Content2
import com.cmc15th.pluv.core.designsystem.theme.Gray600
import com.cmc15th.pluv.core.designsystem.theme.Gray800
import com.cmc15th.pluv.core.designsystem.theme.Title2
import com.cmc15th.pluv.core.designsystem.theme.Title5

@Composable
fun PlaylistInfo(
    modifier: Modifier = Modifier,
    playlistName: String,
    totalMusicCount: Int,
    lastUpdateDate: String,
    userName: String,
) {
    Column(
        modifier = modifier
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {

            Icon(
                painter = painterResource(id = R.drawable.menu_04),
                modifier = Modifier.size(20.dp),
                contentDescription = null
            )

            Spacer(modifier = Modifier.size(8.dp))

            Text(
                text = playlistName,
                style = Title2,
            )
        }

        Spacer(modifier = Modifier.height(20.dp))

        Row {
            Text(text = "총 ${totalMusicCount}곡", style = Content2, color = Gray600)
            Spacer(modifier = Modifier.size(6.dp))
            Text(text = lastUpdateDate, style = Content2, color = Gray600)
        }

        Spacer(modifier = Modifier.size(10.dp))

        Text(text = "공유한 사람: $userName", style = Title5, color = Gray800)
    }
}