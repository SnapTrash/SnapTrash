package com.snaptrash.snaptrash.viewmodel

import android.content.ContentValues.TAG
import android.util.Log
import android.widget.Toast
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.google.android.gms.tasks.Task
import com.google.firebase.FirebaseNetworkException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthEmailException
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import com.google.firebase.auth.FirebaseAuthInvalidUserException
import com.google.firebase.auth.FirebaseAuthMissingActivityForRecaptchaException
import com.google.firebase.auth.FirebaseAuthWeakPasswordException
import com.google.firebase.auth.FirebaseAuthWebException
import com.google.firebase.auth.FirebaseUser
//import com.google.firebase.functions.FirebaseFunctions
import com.google.firebase.functions.FirebaseFunctionsException
import com.snaptrash.snaptrash.R


class LoginViewModel: ViewModel() {
    var email = mutableStateOf("")
    var password = mutableStateOf("")
    var error = mutableStateOf<Int?>(null)
    var inProgress = mutableStateOf(false)
    private val auth = FirebaseAuth.getInstance()
    fun login() {
        inProgress.value = true
        auth.signInWithEmailAndPassword(email.value, password.value)
            .addOnFailureListener {
                inProgress.value = false
                when (it) {
                    is FirebaseAuthEmailException -> {
                        error.value = R.string.error_badly_formatted_e_mail_address
                    }

                    is FirebaseAuthInvalidUserException -> {
                        error.value = R.string.error_invalid_user_or_password
                    }

                    is FirebaseAuthInvalidCredentialsException -> {
                        error.value = R.string.error_invalid_user_or_password
                    }

                    is FirebaseAuthMissingActivityForRecaptchaException -> {
                        error.value = R.string.error_recaptcha
                    }

                    is FirebaseNetworkException -> {
                        error.value = R.string.R_network_error
                    }
                    else -> {
                        error.value = R.string.error_unknown_error
                    }
                }
            }
    }
}