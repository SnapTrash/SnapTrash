package com.snaptrash.snaptrash.model.data

import com.google.android.gms.tasks.Task
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.PropertyName
import com.google.firebase.firestore.QuerySnapshot
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

data class UserSnapReference(
    @PropertyName("id_snap")
    var snapId: DocumentReference
){
    fun getResolverTask(): Task<DocumentSnapshot> {
        return snapId.get()
    }
}
