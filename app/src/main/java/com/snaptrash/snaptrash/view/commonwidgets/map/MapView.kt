package com.snaptrash.snaptrash.view.commonwidgets.map

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.location.Location
import android.location.LocationManager
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.displayCutoutPadding
import androidx.compose.foundation.layout.safeGesturesPadding
import androidx.compose.foundation.systemGestureExclusion
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.viewinterop.AndroidView
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat.getSystemService
import org.osmdroid.util.GeoPoint
import org.osmdroid.views.MapView
import org.osmdroid.views.overlay.TilesOverlay
import org.osmdroid.views.overlay.mylocation.GpsMyLocationProvider
import org.osmdroid.views.overlay.mylocation.MyLocationNewOverlay

@Composable
fun MapView(
    modifier: Modifier = Modifier,
    onLoad: ((map: MapView) -> Unit)? = null
) {
    val mapViewState = rememberMapViewWithLifecycle()
    val context = LocalContext.current
    val nightMode = isSystemInDarkTheme()
    AndroidView(
        { mapViewState },
    ) { mapView ->
        run {
            mapView.clipToOutline = true
            val locationOverlay = MyLocationNewOverlay(GpsMyLocationProvider(context), mapView)
            locationOverlay.enableMyLocation()
            mapView.overlays.add(locationOverlay)
            var locationManager: LocationManager = context.getSystemService(Context.LOCATION_SERVICE) as LocationManager
            var geoPoint = GeoPoint(0.0,0.0)
            if (ActivityCompat.checkSelfPermission(
                    context,
                    Manifest.permission.ACCESS_FINE_LOCATION
                ) == PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                    context,
                    Manifest.permission.ACCESS_COARSE_LOCATION
                ) == PackageManager.PERMISSION_GRANTED
            ) {
                val location = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER)
                geoPoint = GeoPoint(location?.latitude ?: 0.0,location?.longitude ?: 0.0)
            }
            if(nightMode) mapView.overlayManager.tilesOverlay.setColorFilter(TilesOverlay.INVERT_COLORS)
            mapView.controller.setCenter(geoPoint)
            onLoad?.invoke(mapView)
        }
    }
}
