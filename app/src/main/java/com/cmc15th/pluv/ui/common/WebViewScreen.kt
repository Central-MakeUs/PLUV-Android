package com.cmc15th.pluv.ui.common

import android.annotation.SuppressLint
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import com.cmc15th.pluv.core.designsystem.component.TopAppBar

@SuppressLint("SetJavaScriptEnabled")
@Composable
fun WebViewScreen(
    title: String = "",
    url: String = "",
    modifier: Modifier = Modifier,
    onBackClick: () -> Unit = {}
) {
    val context = LocalContext.current

    Column(
        modifier = modifier.fillMaxSize()
    ) {
        TopAppBar(
            description = title,
            onBackClick = {
                onBackClick()
            }
        )
        AndroidView(
            factory = {
                WebView(context).apply {
                    settings.run {
                        javaScriptEnabled = true
                        defaultTextEncodingName = "UTF-8"
                        domStorageEnabled = true // 로컬 저장소 사용 가능
                        loadWithOverviewMode = true // 컨텐츠가 웹뷰보다 클 경우, 컨텐츠 크기에 맞게 조정
                        javaScriptCanOpenWindowsAutomatically = true
                    }
                    webViewClient = WebViewClient()
                    loadUrl(url)
                }
            },
            update = {
                it.loadUrl(url)
            },
            onRelease = {
                // WebView 해제 시 리소스 정리
                it.stopLoading()
                it.clearHistory()
                it.removeAllViews()
                it.destroy()
            },
            modifier = Modifier.fillMaxSize().padding(horizontal = 20.dp)
        )
    }
}