package com.cmc15th.pluv.core.data.mapper

import com.cmc15th.pluv.core.model.GoogleAccessToken
import com.cmc15th.pluv.core.model.JwtToken
import com.cmc15th.pluv.core.network.response.GoogleAccessTokenResponse
import com.cmc15th.pluv.core.network.response.LoginResponse

fun LoginResponse.toJwtToken() = JwtToken(
    accessToken = this.accessToken
)

fun GoogleAccessTokenResponse.toAccessToken() = GoogleAccessToken(
    accessToken = this.accessToken
)