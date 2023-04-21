package com.snaptrash.snaptrash.model.data

import com.google.android.gms.tasks.Task
import com.google.firebase.firestore.*
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

// This class is intended for storing other users' information.
data class User(
    @DocumentId
    val id: String,
    val name: String,
    @PropertyName("full_name")
    val fullName: String,
    @PropertyName("profile_image")
    val profileImage: String,
    @PropertyName("id_association")
    val associationId: String?,
    @PropertyName("is_admin")
    val isAdmin: Boolean
){
    fun getSnapFetchingTask(): Task<QuerySnapshot> {
        return Firebase.firestore.collection("/snaps").whereEqualTo("user",id).get()
    }
    fun getPrivateDataFetchingTask(): Task<QuerySnapshot> {
        return Firebase.firestore.collection("users/${this.id}/private").get()
    }
    fun getReportFetchingTask(): Task<QuerySnapshot> {
        return Firebase.firestore.collection("users/${this.id}/reports").get()
    }
    fun getPointHistoryTask(): Task<QuerySnapshot>{
        return Firebase.firestore.collection("users/${this.id}/pointsHistory").get()
    }
}
