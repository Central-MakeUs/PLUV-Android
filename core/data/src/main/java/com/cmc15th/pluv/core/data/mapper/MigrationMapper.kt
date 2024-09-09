package com.cmc15th.pluv.core.data.mapper

import com.cmc15th.pluv.core.model.MigrationProcess
import com.cmc15th.pluv.core.network.response.MigrationProcessResponse

fun MigrationProcessResponse.toMigrationProcess() = MigrationProcess(
    willTransferMusicCount = willTransferMusicCount,
    transferredMusicCount = transferredMusicCount
)