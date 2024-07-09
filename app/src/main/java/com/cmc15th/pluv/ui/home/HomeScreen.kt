package com.cmc15th.pluv.ui.home

import androidx.annotation.StringRes
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.cmc15th.pluv.R

@Composable
fun MigrationMethodColumn(
    modifier: Modifier = Modifier,
    onDirectClick: () -> Unit,
    onScreenShotClick: () -> Unit
) {
    Column(
        modifier = modifier
    ) {
        MigrationMethodItem(
            R.string.migration_direct,
            onClick = onDirectClick,
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.size(12.dp))
        MigrationMethodItem(
            R.string.migration_screenshot,
            onClick = onScreenShotClick,
            modifier = Modifier.fillMaxWidth()
        )
    }
}

@Composable
fun MigrationMethodItem(
    @StringRes text: Int,
    modifier: Modifier = Modifier,
    onClick: () -> Unit
) {
    Card(
        modifier = modifier.height(80.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White)
    ) {
        Box(
            modifier = modifier
                .clickable(onClick = onClick)
                .fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 18.dp)
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        painterResource(id = R.drawable.grayplaceholder),
                        contentDescription = null,
                        modifier = Modifier.size(30.dp)
                    )
                    Text(
                        text = stringResource(id = text),
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Medium,
                        modifier = Modifier.padding(start = 13.dp)
                    )
                }
                Icon(
                    painter = painterResource(id = R.drawable.rightarrow),
                    contentDescription = null,
                    modifier = Modifier.size(24.dp) // 아이콘 크기 조정
                )
            }
        }
    }
}