package com.snaptrash.snaptrash.viewmodel

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel

class RootNavViewModel: ViewModel() {
    val isLoggedIn = mutableStateOf(false)
    var displayName = mutableStateOf("")
    var email = mutableStateOf("")
}