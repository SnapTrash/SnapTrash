package com.snaptrash.snaptrash.viewmodel

import android.content.Context
import android.content.pm.PackageManager
import android.location.LocationManager
import android.os.Build
import android.provider.Settings
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.core.app.ActivityCompat
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.Filter
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.snaptrash.snaptrash.model.data.Snap
import com.snaptrash.snaptrash.model.data.SnapStatus
import org.osmdroid.util.GeoPoint

class MainNavViewModel : ViewModel(){
    var currentLocation = mutableStateOf<GeoPoint>(GeoPoint(65.0,25.4))
    var snapList = mutableStateListOf<Snap>()
    var locationEnabled = mutableStateOf(false)
    var cameraEnabled = mutableStateOf(false)
    var currentFloatingActionButton: MutableState<@Composable () -> Unit> = mutableStateOf({})
    init{
        Firebase.firestore.collection("/snaps").where(
            Filter.equalTo("user",Firebase.auth.currentUser?.uid)).addSnapshotListener { snaps, ex ->
            run {
                if (snaps != null) {
                    snapList.clear()
                    snapList.addAll(snaps.toObjects(Snap::class.java))
                }
            }
        }
    }
    fun checkPermissions(context: Context){
        val locationManager = context.getSystemService(Context.LOCATION_SERVICE) as LocationManager
        val providerEnabled = if(Build.VERSION.SDK_INT > 30)
            locationManager.isProviderEnabled(LocationManager.FUSED_PROVIDER)
        else
            locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)
        cameraEnabled.value = ActivityCompat.checkSelfPermission(
            context,
            android.Manifest.permission.CAMERA
        ) == PackageManager.PERMISSION_GRANTED
        locationEnabled.value = providerEnabled
    }
}