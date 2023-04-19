package com.snaptrash.snaptrash.viewmodel

import android.telephony.PhoneNumberUtils
import android.util.Patterns
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.ktx.auth
import com.google.firebase.functions.ktx.functions
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.launch
import java.util.Date

class SignUpViewModel: ViewModel() {
    var fullName = mutableStateOf("")
    var dateOfBirth = mutableStateOf(Date())
    var phoneNumber = mutableStateOf("")
    val phoneNumberValid: Boolean get() {
        return PhoneNumberUtils.isGlobalPhoneNumber(phoneNumber.value)
    }
    var email = mutableStateOf("")
    val emailValid: Boolean get(){
        return Patterns.EMAIL_ADDRESS.matcher(email.value).matches()
    }
    var password = mutableStateOf("")
    val passwordValid: Boolean get(){
        val isLongEnough: (String) -> Boolean = {it.length >= 8}
        val hasDigit: (String) -> Boolean = {it.count(Char::isDigit) > 0}
        val isMultiCase: (String) -> Boolean = {it.count(Char::isUpperCase) > 0 && it.count(Char::isLowerCase) > 0}
        val reqs = listOf(isLongEnough,hasDigit,isMultiCase)
        return reqs.all{it(password.value)}
    }
    var error = mutableStateOf("")
    val fieldsValid: Boolean get(){
        return emailValid && passwordValid && phoneNumberValid
    }
    var inProgress = mutableStateOf(false)
    fun register(){
        viewModelScope.launch {
            if(fieldsValid) {
                val functions = Firebase.functions
                inProgress.value = true
                functions.getHttpsCallable("registerAccount").call(
                    hashMapOf(
                        "fullName" to fullName.value,
                        "email" to email.value,
                        "phoneNumber" to phoneNumber.value,
                        "password" to password.value,
                        "birthDate" to dateOfBirth.value
                    )
                ).addOnSuccessListener {
                    Firebase.auth.signInWithEmailAndPassword(email.value,password.value)
                    inProgress.value = false
                    //TODO: put navigation code here
                }.addOnFailureListener {
                    //TODO: look up the proper locale for the error message, if exists
                    error.value = it.message ?: ""
                    inProgress.value = false
                }
            }
        }
    }
}