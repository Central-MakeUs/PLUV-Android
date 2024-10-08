package com.cmc15th.pluv.core.designsystem.component

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.cmc15th.pluv.core.designsystem.R
import com.cmc15th.pluv.core.designsystem.theme.Content2
import com.cmc15th.pluv.core.designsystem.theme.Gray800
import com.cmc15th.pluv.core.designsystem.theme.TopAppBarProgress

@Composable
fun TopAppBar(
    description: String,
    onBackClick: () -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {
        Icon(
            painter = painterResource(id = R.drawable.leftarrow),
            modifier = Modifier
                .size(24.dp)
                .clickable { onBackClick() }
                .align(Alignment.CenterStart),
            contentDescription = null
        )
        
        Text(text = description, modifier = Modifier.align(Alignment.Center))
    }
}

@Composable
fun TopBarWithProgress(
    modifier: Modifier = Modifier,
    totalStep: Int,
    currentStep: Int,
    sourceApp: String = "",
    destinationApp: String = "",
    onCloseClick: () -> Unit
) {

    val progress = currentStep.toFloat() / totalStep

    Column {

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 24.dp, end = 14.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            SourceToDestinationText(sourceApp = sourceApp, destinationApp = destinationApp)
            IconButton(
                onClick = {
                    onCloseClick()
                },
                modifier = Modifier
                    .size(46.dp)
                    .padding(10.dp)
            ) {
                Icon(
                    modifier = Modifier.fillMaxSize(),
                    imageVector = Icons.Default.Close,
                    contentDescription = "Close",
                )
            }
        }

        LinearProgressIndicator(
            progress = progress,
            modifier = Modifier
                .fillMaxWidth()
                .height(4.dp),
            color = TopAppBarProgress,
            trackColor = Color.LightGray,
        )
    }
}

@Composable
fun SourceToDestinationText(
    sourceApp: String,
    destinationApp: String,
    modifier: Modifier = Modifier
) {
    Text(
        modifier = modifier,
        text = if (sourceApp.isEmpty()) "" else "$sourceApp > $destinationApp",
        style = Content2,
        color = Gray800
    )
}

@Preview
@Composable
fun TopBarWithProgressPreview() {
    TopBarWithProgress(
        totalStep = 5,
        currentStep = 1,
        onCloseClick = {}
    )
}

@Preview
@Composable
fun TopAppBarPreview() {
    TopAppBar(
        description = "Title",
        onBackClick = {}
    )
}