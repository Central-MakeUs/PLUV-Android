package com.cmc15th.pluv.core.designsystem.component

import androidx.compose.foundation.layout.size
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.cmc15th.pluv.core.designsystem.theme.PrimaryDefault

@Composable
fun LoadingIndicator(
    modifier: Modifier = Modifier
) {
    CircularProgressIndicator(
        modifier = modifier.size(50.dp),
        color = MaterialTheme.colorScheme.secondary,
        trackColor = PrimaryDefault
    )
}
