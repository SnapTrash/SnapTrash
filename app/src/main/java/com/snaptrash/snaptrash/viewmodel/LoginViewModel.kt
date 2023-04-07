package com.snaptrash.snaptrash.viewmodel

import androidx.lifecycle.ViewModel
import com.google.android.gms.tasks.Task
import com.google.firebase.functions.FirebaseFunctions



class LoginViewModel: ViewModel() {
    var email: String = ""
    var password: String = ""
    var isLogin: Boolean = false
    //Initialize an instance of Cloud Functions
    private val functions = FirebaseFunctions.getInstance()
    private fun onLogin(email: String, password: String, isLogin: Boolean): Task<String> {
        // Create the arguments to the callable function.
        val data = hashMapOf(
            "email" to email,
            "password" to password,
            "isLogin" to isLogin
        )

        return functions
            .getHttpsCallable("onLogin")
            .call(data)
            .continueWith { task ->
                // This continuation runs on either success or failure, but if the task
                // has failed then result will throw an Exception which will be
                // propagated down.
                val result = task.result?.data as String
                result
            }
    }


}