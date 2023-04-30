package com.snaptrash.snaptrash.viewmodel

import android.net.Uri
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.navigation.NavController
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.GeoPoint
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.ktx.storage
import com.google.type.Date
import com.snaptrash.snaptrash.R
import com.snaptrash.snaptrash.model.data.Snap
import com.snaptrash.snaptrash.model.data.SnapStatus
import com.snaptrash.snaptrash.model.data.Urgency
import com.snaptrash.snaptrash.view.navigator.MainAddressBook
import java.time.LocalDateTime

class SnapScreenViewModel: ViewModel() {
    var snapUrl = mutableStateOf(Uri.EMPTY)
    var snapId = ""
    var isNew = false
    var location = mutableStateOf(GeoPoint(0.0,0.0))
    var description = mutableStateOf("")
    var urgency = mutableStateOf(Urgency.NOT_URGENT)
    var error : MutableState<Int?> = mutableStateOf(null)
    var inProgress = mutableStateOf(false)
    lateinit var navController: NavController

    fun doSnapFunction(){
        inProgress.value = true
        if(isNew){
            val filename = "/snapImages/${Firebase.auth.uid}/${LocalDateTime.now()}.jpg"
            Firebase.storage.getReference(filename).putFile(snapUrl.value).addOnSuccessListener {
                val snap = Snap("",location.value, Firebase.auth.uid!!,filename,"",
                    java.util.Date(), description.value,urgency.value,urgency.value,SnapStatus.PENDING)
                snap.submitSnap().addOnSuccessListener {
                    inProgress.value = false
                    navController.popBackStack(MainAddressBook.HOME,false)
                }.addOnFailureListener {
                    inProgress.value = false
                    error.value = R.string.error_snap_creation
                }
            }.addOnFailureListener{
                inProgress.value = false
                error.value = R.string.error_snap_image
            }
        }
        else{
            Firebase.firestore.collection("/snaps/").document(snapId).delete().addOnSuccessListener {
                inProgress.value = false
                navController.popBackStack(MainAddressBook.HOME,false)
            }.addOnFailureListener {
                inProgress.value = false
                error.value = R.string.error_snap_deletion
            }
        }
    }
}