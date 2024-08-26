package com.cmc15th.pluv.core.network.response

data class MigrationProcessResponse(
    val willTransferMusicCount: Int,
    val transferredMusicCount: Int
)