package com.cmc15th.pluv.feature.migrate.viewmodel

import android.net.Uri
import com.cmc15th.pluv.core.model.PlayListApp
import com.cmc15th.pluv.core.model.Playlist
import com.cmc15th.pluv.core.model.SourceMusic
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.tasks.Task
import com.spotify.sdk.android.auth.AuthorizationResponse

sealed class DirectMigrationUiEvent {
    class SelectSourceApp(val selectedApp: PlayListApp) : DirectMigrationUiEvent()
    class SelectDestinationApp(val selectedApp: PlayListApp) : DirectMigrationUiEvent()
    data object FetchSavedFeed : DirectMigrationUiEvent()
    data object FetchHistory : DirectMigrationUiEvent()
    data class GoogleLogin(val task: Task<GoogleSignInAccount>?) : DirectMigrationUiEvent()
    data class SpotifyLogin(val task: AuthorizationResponse) : DirectMigrationUiEvent()
    data object OnSourceLoginSuccess : DirectMigrationUiEvent()
    data object OnDestinationLoginSuccess : DirectMigrationUiEvent()
    class SelectPlaylist(val playlist: Playlist) : DirectMigrationUiEvent()
    data object FetchMusicsByPlaylist : DirectMigrationUiEvent()
    class SelectSourceMusic(val selectedMusic: SourceMusic) : DirectMigrationUiEvent()
    class SelectAllSourceMusic(val selectAllFlag: Boolean) : DirectMigrationUiEvent()
    class SelectSimilarMusic(val index: Int, val selectedMusicId: String) : DirectMigrationUiEvent()
    data object ExecuteMigration : DirectMigrationUiEvent()
    data object OnMigrationSucceed : DirectMigrationUiEvent()
    data object ShowExitMigrationDialog : DirectMigrationUiEvent()
    data class OnAddScreenShot(val uris: List<Uri>) : DirectMigrationUiEvent()
    data class OnDeleteScreenShot(val index: Int) : DirectMigrationUiEvent()
    data object FetchScreenShot : DirectMigrationUiEvent()
    data object FetchProcess : DirectMigrationUiEvent()
}