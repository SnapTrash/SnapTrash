package com.snaptrash.snaptrash.viewmodel

import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.snaptrash.snaptrash.model.data.Snap
import org.osmdroid.util.GeoPoint

class MainNavViewModel : ViewModel(){
    var currentLocation = mutableStateOf<GeoPoint>(GeoPoint(65.0,25.4))
}