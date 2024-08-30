package com.cmc15th.pluv.ui.common

import android.content.Context
import android.net.Uri
import android.util.Base64
import javax.inject.Inject

class ImageEncoder @Inject constructor(
    private val context: Context,
) {
    fun encodeImageUriToBase64(uri: Uri): String =
        context.contentResolver.openInputStream(uri)?.use { inputStream ->
            val bytes = inputStream.readBytes()
            Base64.encodeToString(bytes, Base64.DEFAULT)
        } ?: throw IllegalArgumentException("Failed to open input stream for URI")
    }
