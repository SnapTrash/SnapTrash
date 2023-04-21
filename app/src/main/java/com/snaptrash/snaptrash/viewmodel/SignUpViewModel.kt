package com.snaptrash.snaptrash.viewmodel

import android.content.Context
import android.telephony.PhoneNumberUtils
import android.util.Patterns
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavController
import com.google.firebase.auth.ktx.auth
import com.google.firebase.functions.ktx.functions
import com.google.firebase.ktx.Firebase
import com.google.i18n.phonenumbers.PhoneNumberUtil
import com.google.i18n.phonenumbers.Phonenumber.PhoneNumber
import com.snaptrash.snaptrash.R
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import java.time.LocalDate
import java.util.Date
import kotlin.coroutines.Continuation
import kotlin.coroutines.createCoroutine
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

class SignUpViewModel: ViewModel() {
    var firstName = mutableStateOf("")
    var lastName = mutableStateOf("")
    private val fullName: String get(){ return "${firstName.value} ${lastName.value}"}
    var dateOfBirth = mutableStateOf(LocalDate.now())
    var phoneNumber = mutableStateOf("")
    val phoneNumberValid: Boolean get() {
        return try {
            val phoneNum = PhoneNumberUtil.getInstance().parse(phoneNumber.value,"fi")
            PhoneNumberUtil.getInstance().isValidNumber(phoneNum)
        } catch(e: Exception){
            false
        }
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
    var error = mutableStateOf(-1)
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
                        "fullName" to fullName,
                        "email" to email.value,
                        "phoneNumber" to phoneNumber.value,
                        "password" to password.value,
                        "birthDate" to dateOfBirth.value.toString()
                    )
                ).addOnSuccessListener {
                    suspend{
                        // Wait for Firebase to catch up
                        delay(1000)
                    }.createCoroutine(Continuation(viewModelScope.coroutineContext) {
                        Firebase.auth.signInWithEmailAndPassword(
                            email.value,
                            password.value
                        ).addOnFailureListener {
                            // Retry login
                            Firebase.auth.signInWithEmailAndPassword(
                                email.value,
                                password.value
                            ).addOnFailureListener {
                                // Retry for last time
                                Firebase.auth.signInWithEmailAndPassword(
                                    email.value,
                                    password.value
                                ).addOnFailureListener {
                                    inProgress.value = false
                                    // Add error message display
                                }
                            }
                        }
                    }).resume(Unit)
                }.addOnFailureListener {
                    error.value = when(it.message){
                        "1" -> R.string.error_at_least_one_field_is_missing
                        "2" -> R.string.error_at_least_one_of_the_fields_is_invalid
                        else -> R.string.error_signup_unknown_error
                    }
                    inProgress.value = false
                }
            }
        }
    }
}