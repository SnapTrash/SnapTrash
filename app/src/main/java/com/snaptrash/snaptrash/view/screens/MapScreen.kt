package com.snaptrash.snaptrash.view.screens

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavController
import com.snaptrash.snaptrash.view.commonwidgets.map.MapView
import org.osmdroid.util.GeoPoint
import org.osmdroid.views.overlay.TilesOverlay

@Composable
fun MapScreen(navController: NavController,location: GeoPoint){
    val context = LocalContext.current
    val nightMode = isSystemInDarkTheme()
    var centerSet by remember { mutableStateOf(false) }
    MapView(
        Modifier.fillMaxSize()
    ) {
        it.controller.setZoom(14.6)
        it.minZoomLevel = 5.6
        it.maxZoomLevel = 20.0
        it.setMultiTouchControls(true)
        it.setMultiTouchControls(true)
        if(!centerSet){
            it.controller.setCenter(location)
            centerSet = true
        }
    }
}