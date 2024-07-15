package com.cmc15th.pluv.ui.home.migrate.direct

sealed class DirectMigrationUiEffect {
    data object onSuccess : DirectMigrationUiEffect()
    data object onFailure : DirectMigrationUiEffect()
}