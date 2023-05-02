package com.snaptrash.snaptrash.viewmodel

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.FirebaseTooManyRequestsException
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.functions.ktx.functions
import com.google.firebase.ktx.Firebase
import com.snaptrash.snaptrash.R
import kotlinx.coroutines.launch

class NameChangeViewModel: ViewModel() {
    var firstName = mutableStateOf("")
    var lastName = mutableStateOf("")

    val fullName : String get(){ return "${firstName.value} ${lastName.value}"}
    var error = mutableStateOf<Int?>(null)
    var success = mutableStateOf<Int?>(null)
    var inProgress = mutableStateOf(false)
    fun changeName(){
        inProgress.value = true
        Firebase.firestore.collection("users").document(Firebase.auth.uid!!).update(mapOf(
                "full_name" to fullName)
            ).addOnSuccessListener { inProgress.value = false
            success.value = R.string.name_changing_success
            Firebase.auth.currentUser!!.reload()
            error.value = null}
            .addOnFailureListener {
                error.value = R.string.name_changing_error
                inProgress.value = false
                success.value = null
            }
    }
}
