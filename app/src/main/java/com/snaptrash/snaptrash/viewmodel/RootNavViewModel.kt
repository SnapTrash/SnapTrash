package com.snaptrash.snaptrash.viewmodel

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.navigation.NavHostController

class RootNavViewModel: ViewModel() {
    val isLoggedIn = mutableStateOf(false)
}