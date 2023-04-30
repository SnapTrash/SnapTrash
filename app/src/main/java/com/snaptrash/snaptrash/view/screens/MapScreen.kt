package com.snaptrash.snaptrash.view.screens

import android.R
import android.content.res.Resources
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import android.graphics.drawable.Icon
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.core.content.res.ResourcesCompat
import androidx.core.graphics.scale
import androidx.navigation.NavController
import com.snaptrash.snaptrash.model.data.SnapStatus
import com.snaptrash.snaptrash.view.commonwidgets.map.MapView
import com.snaptrash.snaptrash.viewmodel.MainNavViewModel
import org.osmdroid.util.GeoPoint
import org.osmdroid.views.overlay.Marker


@Composable
fun MapScreen(navController: NavController,mainNavViewModel: MainNavViewModel){
    val context = LocalContext.current
    val configuration = LocalConfiguration.current
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
            it.controller.setCenter(mainNavViewModel.currentLocation.value)
            centerSet = true
        }
        mainNavViewModel.snapList.forEach { snap ->
            val marker: Marker = Marker(it)
            var kindIcon = com.snaptrash.snaptrash.R.drawable.pointer_green
            if (snap.status == SnapStatus.PENDING) {
                kindIcon = com.snaptrash.snaptrash.R.drawable.pointer_blue

            }
            marker.icon = ResourcesCompat.getDrawable(context.resources, kindIcon ,null)
            val bitmap = (marker.icon as BitmapDrawable).bitmap
            val ratio = (bitmap.width.toFloat() / bitmap.height.toFloat())
            val newWidth = (configuration.screenWidthDp.toFloat() * 0.1).toInt()
            val newHeight = (newWidth / ratio).toInt()
            marker.icon = BitmapDrawable(context.resources,
                bitmap.scale(newWidth,newHeight))
            //marker.image = Drawable.createFromPath("drawable://" + com.snaptrash.snaptrash.R.drawable.pointer_green)
            //marker.setTextIcon("S")
            marker.position = GeoPoint(snap.location.latitude, snap.location.longitude)
            marker.setAnchor(Marker.ANCHOR_CENTER, Marker.ANCHOR_BOTTOM)

            it.overlays.add(marker)
            it.invalidate()
        }
    }
}