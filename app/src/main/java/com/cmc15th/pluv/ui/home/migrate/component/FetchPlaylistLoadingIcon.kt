package com.cmc15th.pluv.ui.home.migrate.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.unit.dp
import com.cmc15th.pluv.R

@Composable
fun FetchPlaylistLoadingIcon() {
    Column(
        verticalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        LoadingDialogIconContent()
        LoadingDialogIconContent()
        LoadingDialogIconContent()
    }
}

@Composable
private fun LoadingDialogIconContent() {
    Box(
        modifier = Modifier
            .width(72.dp)
            .height(17.dp)
            .background(
                color = colorResource(id = R.color.loading_playlist_icon_color),
                shape = RoundedCornerShape(2.dp)
            )
    )
}