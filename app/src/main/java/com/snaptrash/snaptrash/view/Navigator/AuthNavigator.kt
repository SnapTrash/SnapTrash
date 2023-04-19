package com.snaptrash.snaptrash.view.Navigator

import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.snaptrash.snaptrash.view.LoginScreen.LoginScreen
import com.snaptrash.snaptrash.view.SignUpScreen.SignUpScreen

class AuthAddressBook{
    companion object {
        val LOGIN = "login"
        val SIGN_UP = "signup"
    }
    fun addAuthToGraph(navController: NavController,navGraphBuilder: NavGraphBuilder){
        navGraphBuilder.composable(route = LOGIN){
            LoginScreen(navController)
        }
        navGraphBuilder.composable(route = SIGN_UP){
            SignUpScreen(navController)
        }
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LoginNavigation(
    navController: NavHostController
) {
    val coroutineScope = rememberCoroutineScope()
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    //val primaryColorTrasparent = MaterialTheme.colorScheme.primary.copy(alpha = 0.5f)
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
