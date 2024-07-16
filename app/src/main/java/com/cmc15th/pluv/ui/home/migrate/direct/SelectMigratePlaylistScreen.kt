package com.cmc15th.pluv.ui.home.migrate.direct

import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.itemsIndexed
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.cmc15th.pluv.R
import com.cmc15th.pluv.core.designsystem.theme.Content2
import com.cmc15th.pluv.core.designsystem.theme.Title1
import com.cmc15th.pluv.core.designsystem.theme.Title4
import com.cmc15th.pluv.core.model.Playlist
import com.cmc15th.pluv.ui.home.migrate.component.PreviousOrMigrateButton
import com.cmc15th.pluv.ui.home.migrate.component.SourceToDestinationText

@Composable
fun SelectMigratePlaylistScreen(
    modifier: Modifier = Modifier,
    viewModel: DirectMigrationViewModel = hiltViewModel(),
    navigateToDisplayMigrationPath: () -> Unit,
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(24.dp)
    ) {
        SourceToDestinationText(
            sourceApp = uiState.selectedSourceApp.name,
            destinationApp = uiState.selectedDestinationApp.name
        )
        Spacer(modifier = Modifier.size(8.dp))
        Text(text = "옮길 플레이리스트를 선택 해주세요", style = Title1)
        Spacer(modifier = Modifier.size(28.dp))
        Text(
            text = "최대 1개",
            style = Content2,
            textAlign = TextAlign.End,
            modifier = Modifier
                .fillMaxWidth()
                .padding(12.dp)
        )
        PlaylistColumn(
            playlists = uiState.allPlaylists,
            selectedPlaylist = uiState.selectedPlaylist,
            modifier = Modifier.padding(15.dp),
            onPlaylistSelect = {}
        )
        Spacer(modifier = Modifier.weight(1f))
        PreviousOrMigrateButton(
            modifier = Modifier.size(58.dp),
            isNextButtonEnabled = uiState.selectedPlaylist != -1L,
            onPreviousClick = { navigateToDisplayMigrationPath() },
            onMigrateClick = { /*TODO*/ }
        )
    }
}

@Composable
fun PlaylistColumn(
    playlists: List<Playlist>,
    selectedPlaylist: Long,
    modifier: Modifier = Modifier,
    onPlaylistSelect: (Long) -> Unit
) {

    LazyVerticalGrid(
        columns = GridCells.Fixed(2),
        verticalArrangement = Arrangement.spacedBy(32.dp),
        modifier = modifier
    ) {
        itemsIndexed(playlists) { index, playlist ->
            Image(
                painter = painterResource(id = R.drawable.ic_launcher_background),
                contentDescription = null,
                modifier = Modifier
                    .size(140.dp)
                    .border(
                        if (playlist.id == selectedPlaylist) 2.dp else 0.dp,
                        colorResource(id = R.color.destination_app_title_color)
                    )
                    .clickable { onPlaylistSelect(playlist.id) }
            )
//            PlaylistCard(imageUrl = "")
        }
    }
}

@Composable
fun SelectPlaylistText() {
    Text(text = "플레이리스트 선택", style = Title4)
}

@Preview
@Composable
fun SelectMigratePlaylistScreenPreview() {
    SelectMigratePlaylistScreen(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        viewModel = DirectMigrationViewModel(),
        navigateToDisplayMigrationPath = {}
    )
}

