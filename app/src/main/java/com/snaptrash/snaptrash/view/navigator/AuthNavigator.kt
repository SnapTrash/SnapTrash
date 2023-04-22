package com.snaptrash.snaptrash.view.navigator

import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.snaptrash.snaptrash.view.screens.LoginScreen
import com.snaptrash.snaptrash.view.screens.SignUpScreen

class AuthAddressBook{
    companion object {
        const val LOGIN = "login"
        const val SIGN_UP = "signup"
    }
}


@Composable
fun LoginNavigation(
    navController: NavHostController
) {
        NavHost(
            navController = navController,
            startDestination = "login"

        ){
            composable("login"){
                LoginScreen(navController)
            }
            composable("signup"){
                SignUpScreen(navController)
            }

        }
}
