package com.snaptrash.snaptrash.viewmodel

import android.content.ContentValues.TAG
import android.util.Log
import android.widget.Toast
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
//import com.google.firebase.functions.FirebaseFunctions
import com.google.firebase.functions.FirebaseFunctionsException


class LoginViewModel: ViewModel() {
    var email = mutableStateOf("")
    var password = mutableStateOf("")
    var error = mutableStateOf("")
    private val auth = FirebaseAuth.getInstance()
    fun login() {
        auth.signInWithEmailAndPassword(email.value, password.value)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    // Sign in success, update UI with the signed-in user's information
                    Log.d(TAG, "signInWithEmail:success")
                } else {
                    // If sign in fails, display a message to the user.
                    Log.w(TAG, "signInWithEmail:failure", task.exception)
                }
            }
    }
}