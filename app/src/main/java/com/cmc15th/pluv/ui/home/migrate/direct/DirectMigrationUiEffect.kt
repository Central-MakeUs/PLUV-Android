package com.cmc15th.pluv.ui.home.migrate.direct

sealed class DirectMigrationUiEffect {
    data object OnFetchPlaylistSuccess : DirectMigrationUiEffect()
    data object OnFetchMusicSuccess : DirectMigrationUiEffect()
    data object OnFailure : DirectMigrationUiEffect()
    data object OnValidateMusic : DirectMigrationUiEffect()
}