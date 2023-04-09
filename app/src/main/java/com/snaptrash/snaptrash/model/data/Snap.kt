package com.snaptrash.snaptrash.model.data

import com.google.firebase.firestore.DocumentId
import com.google.firebase.firestore.GeoPoint
import com.google.firebase.firestore.PropertyName
import java.util.Date

data class Snap(
    @DocumentId
    val id: String,
    @PropertyName("coordinates")
    var location: GeoPoint,
    @PropertyName("id_snap_image")
    var snapImageUrl: String,
    var date: Date,
    var description: String,
    var urgency: Urgency
){
    fun submitSnap(){

    }
}
