package com.snaptrash.snaptrash.view.Navigator

import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.snaptrash.snaptrash.view.LoginScreen.LoginScreen
import com.snaptrash.snaptrash.view.SignUpScreen.SignUpScreen


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LoginNavigation(
) {
    val navController = rememberNavController()
    val coroutineScope = rememberCoroutineScope()
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)

    //val primaryColorTrasparent = MaterialTheme.colorScheme.primary.copy(alpha = 0.5f)
        NavHost(
            navController = navController,
            startDestination = "Login"

        ){
            composable("Login"){
                LoginScreen(navController)

            }
            composable("SignUp"){
                SignUpScreen(navController)

            }

        }
}
