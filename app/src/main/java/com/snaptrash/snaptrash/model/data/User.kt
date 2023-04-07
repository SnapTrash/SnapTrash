package com.snaptrash.snaptrash.model.data

import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.PropertyName

// This class is intended for storing other users' information.
data class User(
    val name: String,
    @PropertyName("full_name")
    val fullName: String,
    @PropertyName("profile_image")
    val profileImage: String,
    @PropertyName("id_association")
    val associationId: String?,
    @PropertyName("is_admin")
    val isAdmin: Boolean
)
