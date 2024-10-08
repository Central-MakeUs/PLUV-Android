package com.cmc15th.pluv.feature.migrate.viewmodel

sealed class DirectMigrationUiEffect {
    data object OnLoginSuccess : DirectMigrationUiEffect()
    data object OnFetchPlaylistSuccess : DirectMigrationUiEffect()
    data object OnFetchMusicSuccess : DirectMigrationUiEffect()
    data object OnFailure : DirectMigrationUiEffect()
    data class OnValidateMusic(val needValidate: Boolean) : DirectMigrationUiEffect()
    data object OnMigrationSuccess : DirectMigrationUiEffect()
    data object OnMigrationStart: DirectMigrationUiEffect()
}