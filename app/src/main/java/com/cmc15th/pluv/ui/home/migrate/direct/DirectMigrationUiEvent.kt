package com.cmc15th.pluv.ui.home.migrate.direct

import com.cmc15th.pluv.core.model.Playlist
import com.cmc15th.pluv.core.model.SourceMusic
import com.cmc15th.pluv.core.model.ValidateMusic
import com.cmc15th.pluv.domain.model.PlayListApp
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.tasks.Task
import com.spotify.sdk.android.auth.AuthorizationResponse

sealed class DirectMigrationUiEvent {
    class SelectSourceApp(val selectedApp: PlayListApp) : DirectMigrationUiEvent()
    class SelectDestinationApp(val selectedApp: PlayListApp) : DirectMigrationUiEvent()
    data object ExecuteMigration : DirectMigrationUiEvent()
    data class GoogleLogin(val task: Task<GoogleSignInAccount>?): DirectMigrationUiEvent()
    data class SpotifyLogin(val task: AuthorizationResponse) : DirectMigrationUiEvent()
    data object OnSourceLoginSuccess : DirectMigrationUiEvent()
    data object OnDestinationLoginSuccess: DirectMigrationUiEvent()
    class SelectPlaylist(val playlist: Playlist) : DirectMigrationUiEvent()
    data object FetchMusicsByPlaylist : DirectMigrationUiEvent()
    class SelectSourceMusic(val selectedMusic: SourceMusic) : DirectMigrationUiEvent()
    class SelectAllSourceMusic(val selectAllFlag: Boolean) : DirectMigrationUiEvent()
    class SelectSimilarMusic(val selectedMusic: ValidateMusic) : DirectMigrationUiEvent()
    class SelectAllValidateMusic(val selectAllFlag: Boolean) : DirectMigrationUiEvent()
}