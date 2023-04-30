package com.snaptrash.snaptrash.view.helper

import android.content.Context
import android.content.res.Configuration
import android.graphics.drawable.BitmapDrawable
import androidx.core.content.res.ResourcesCompat
import androidx.core.graphics.scale
import com.snaptrash.snaptrash.model.data.Snap
import com.snaptrash.snaptrash.model.data.SnapStatus
import org.osmdroid.util.GeoPoint
import org.osmdroid.views.MapView
import org.osmdroid.views.overlay.Marker

class MapHelper{
    companion object {
        fun addSnapsToMap(context: Context,configuration: Configuration, mapView: MapView, snaps: List<Snap>) {
            snaps.forEach { snap ->
                val marker: Marker = Marker(mapView)
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
                marker.position = GeoPoint(snap.location.latitude, snap.location.longitude)
                marker.setAnchor(Marker.ANCHOR_CENTER, Marker.ANCHOR_BOTTOM)
                mapView.overlays.add(marker)
            }
        }
    }
}