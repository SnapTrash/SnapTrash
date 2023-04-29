package com.snaptrash.snaptrash.viewmodel

import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.Filter
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.snaptrash.snaptrash.model.data.Snap
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
}