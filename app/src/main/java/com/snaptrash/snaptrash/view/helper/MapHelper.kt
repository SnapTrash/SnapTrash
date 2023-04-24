package com.snaptrash.snaptrash.view.helper

import com.snaptrash.snaptrash.model.data.Snap
import org.osmdroid.util.GeoPoint
import org.osmdroid.views.MapView
import org.osmdroid.views.overlay.Marker

class MapHelper{
    companion object {
        fun addSnapsToMap(mapView: MapView, snaps: List<Snap>) {
            snaps.forEach { snap ->
                val marker: Marker = Marker(mapView)
                marker.position = GeoPoint(snap.location.latitude, snap.location.longitude)
                marker.setAnchor(Marker.ANCHOR_CENTER, Marker.ANCHOR_BOTTOM)
                mapView.overlays.add(marker)
            }
        }
    }
}