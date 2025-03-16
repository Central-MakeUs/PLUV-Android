package com.cmc15th.pluv.core.network

import com.google.gson.Gson

object GsonProvider {
    // Gson 싱글톤 객체
    val instance: Gson by lazy {
        Gson()
    }
}