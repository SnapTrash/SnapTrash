package com.snaptrash.snaptrash.view.screens

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavController
import com.snaptrash.snaptrash.view.commonwidgets.map.MapView
import org.osmdroid.views.overlay.TilesOverlay

@Composable
fun MapScreen(navController: NavController){
    val context = LocalContext.current
    val nightMode = isSystemInDarkTheme()
    MapView(
        Modifier.fillMaxSize()
    ) {
        it.controller.setZoom(14.6)
        it.minZoomLevel = 5.6
        it.maxZoomLevel = 20.0
        it.setMultiTouchControls(true)
        it.setMultiTouchControls(true)
    }
}