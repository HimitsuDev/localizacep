package com.himitsu.localizacep.composes

import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.navigation.NavController


@Composable
fun PoliticasPrivacidade(navController: NavController) {
    val context = LocalContext.current

    Column (Modifier.fillMaxSize()){
        dropMenu(navController)

    Box(
        modifier = Modifier
            .background(Color.Transparent)
            .padding(start = 10.dp, end = 10.dp)
            .fillMaxSize()
            .clickable { }
        ,

        contentAlignment = Alignment.Center
    ) {
        LazyColumn{
            item {
                AndroidView(
                    modifier = Modifier
                        .background(Color.Black),

                    factory = {
                        WebView(context).apply {
                            webViewClient = WebViewClient()

                            loadUrl("https://himitsudev.github.io/himitsu/localizacepprivacy.html")
                        }
                    })
            }
        }

    }
    }
}