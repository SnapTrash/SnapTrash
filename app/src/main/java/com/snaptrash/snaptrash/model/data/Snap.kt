package com.snaptrash.snaptrash.model.data

import com.google.android.gms.tasks.Task
import com.google.firebase.firestore.DocumentId
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.GeoPoint
import com.google.firebase.firestore.PropertyName
import com.google.firebase.firestore.QuerySnapshot
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.functions.HttpsCallableResult
import com.google.firebase.functions.ktx.functions
import com.google.firebase.ktx.Firebase
import java.util.Date

data class Snap(
    @DocumentId
    val id: String,
    @PropertyName("coordinates")
    var location: GeoPoint,
    @PropertyName("id_snap_image")
    var snapImageUrl: String,
    @PropertyName("id_location")
    var locationId: DocumentReference,
    var date: Date,
    var description: String,
    var urgency: Urgency,
){
    fun submitSnap(): Task<HttpsCallableResult> {
        return Firebase.functions.getHttpsCallable("createSnap").call(
            hashMapOf(
                "lat" to location.latitude,
                "lon" to location.longitude,
                "photoURL" to snapImageUrl,
                "urgency" to urgency,
                "description" to description
            )
        )
    }
    fun getAssociationWritableData(): Task<QuerySnapshot>{
        return Firebase.firestore.collection("${locationId.path}/snaps/${id}/associationWritable").limit(1).get()
    }
}
