package com.snaptrash.snaptrash.viewmodel

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.snaptrash.snaptrash.R

class PasswordChangeViewModel: ViewModel() {
    var password = mutableStateOf("")
    var confirmPassword = mutableStateOf("")
    var oldPassword = mutableStateOf("")
    val passwordValid: Boolean get(){
        val isLongEnough: (String) -> Boolean = {it.length >= 8}
        val hasDigit: (String) -> Boolean = {it.count(Char::isDigit) > 0}
        val isSame: (String) -> Boolean = { confirmPassword.value == password.value}
        val isDifferent: (String) -> Boolean = { oldPassword.value != password.value}
        val isMultiCase: (String) -> Boolean = {it.count(Char::isUpperCase) > 0 && it.count(Char::isLowerCase) > 0}
        val reqs = listOf(isLongEnough,hasDigit,isMultiCase,isSame,isDifferent)
        return reqs.all{it(password.value)}
    }
    val fieldsValid: Boolean get(){
        return  passwordValid
    }
    var error = mutableStateOf<Int?>(null)
    var success = mutableStateOf<Int?>(null)
    var inProgress = mutableStateOf(false)
    fun changePassword(){
        if(fieldsValid) {
            inProgress.value = true
            Firebase.auth.currentUser!!.updatePassword(
                password.value
            ).addOnSuccessListener {
                inProgress.value = false
                success.value = R.string.name_changing_success
                error.value = null
            }
                .addOnFailureListener {
                    error.value = R.string.name_changing_error
                    inProgress.value = false
                    success.value = null
                }
        }
    }
}