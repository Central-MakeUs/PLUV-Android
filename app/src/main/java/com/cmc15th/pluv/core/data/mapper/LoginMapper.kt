package com.cmc15th.pluv.core.data.mapper

import com.cmc15th.pluv.core.model.JwtToken
import com.cmc15th.pluv.core.network.response.LoginResponse

fun LoginResponse.toJwtToken() = JwtToken(
    accessToken = this.accessToken
)