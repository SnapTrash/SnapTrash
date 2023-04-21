package com.snaptrash.snaptrash.view.navigator

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.snaptrash.snaptrash.view.HomeScreen.HomeScreen
import com.snaptrash.snaptrash.view.screens.AboutUsScreen
import com.snaptrash.snaptrash.view.screens.AccountScreen
import com.snaptrash.snaptrash.view.screens.CameraScreen
import com.snaptrash.snaptrash.view.screens.CameraView
import com.snaptrash.snaptrash.view.screens.ListSnapScreen

class MainAddressBook{
    companion object {
        const val HOME = "home"
        const val HISTORY = "history"
        const val LIST = "list"
        const val ABOUT = "about"
        const val SETTINGS = "settings"
        const val CAMERA = "camera"
        fun addMainGraph(navGraphBuilder: NavGraphBuilder,navController: NavController){
            navGraphBuilder.composable(MainAddressBook.HOME){
                HomeScreen(navController)
            }
            navGraphBuilder.composable(MainAddressBook.LIST){
                ListSnapScreen()
            }
            navGraphBuilder.composable(MainAddressBook.HISTORY){

            }
            navGraphBuilder.composable(MainAddressBook.SETTINGS){
                AccountScreen()
            }
            navGraphBuilder.composable(MainAddressBook.ABOUT){
                AboutUsScreen()
            }
            navGraphBuilder.composable(MainAddressBook.CAMERA){
                CameraScreen()

            }
        }
    }
}