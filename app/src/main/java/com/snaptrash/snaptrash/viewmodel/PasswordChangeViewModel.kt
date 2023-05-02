package com.snaptrash.snaptrash.viewmodel

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.google.firebase.FirebaseNetworkException
import com.google.firebase.auth.EmailAuthProvider
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.snaptrash.snaptrash.R

class PasswordChangeViewModel: ViewModel() {
    var password = mutableStateOf("")
    var confirmPassword = mutableStateOf("")
    var oldPassword = mutableStateOf("")
    val isSame: Boolean get() { return confirmPassword.value == password.value}
    val passwordValid: Boolean get(){
        val isLongEnough: (String) -> Boolean = {it.length >= 8}
        val hasDigit: (String) -> Boolean = {it.count(Char::isDigit) > 0}
        val isDifferent: (String) -> Boolean = { oldPassword.value != password.value}
        val isMultiCase: (String) -> Boolean = {it.count(Char::isUpperCase) > 0 && it.count(Char::isLowerCase) > 0}
        val reqs = listOf(isLongEnough,hasDigit,isMultiCase,isDifferent)
        return reqs.all{it(password.value)}
    }
    val fieldsValid: Boolean get(){
        return  passwordValid && isSame
    }
    var error = mutableStateOf<Int?>(null)
    var success = mutableStateOf<Int?>(null)
    var inProgress = mutableStateOf(false)
    fun changePassword(){
        if(fieldsValid) {
            inProgress.value = true
            val credential = EmailAuthProvider.getCredential(Firebase.auth.currentUser!!.email!!,oldPassword.value)
            Firebase.auth.currentUser!!.reauthenticate(credential).addOnSuccessListener {
                Firebase.auth.currentUser!!.updatePassword(
                    password.value
                ).addOnSuccessListener {
                    inProgress.value = false
                    success.value = R.string.password_changed_successfully
                    error.value = null
                }
                    .addOnFailureListener {
                        error.value = R.string.name_changing_error
                        inProgress.value = false
                        success.value = null
                    }
            }.addOnFailureListener{
                success.value = null
                inProgress.value = false
                when (it) {
                    is FirebaseNetworkException -> {
                        error.value = R.string.R_network_error
                    }

                    is FirebaseAuthInvalidCredentialsException -> {
                        error.value = R.string.wrong_old_password
                    }

                    else -> {
                        error.value = R.string.error_unknown_error
                    }
                }
            }
        }
    }
}