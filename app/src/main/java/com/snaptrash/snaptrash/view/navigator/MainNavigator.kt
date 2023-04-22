package com.snaptrash.snaptrash.view.navigator

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.snaptrash.snaptrash.view.HomeScreen.HomeScreen
import com.snaptrash.snaptrash.view.screens.*
import com.snaptrash.snaptrash.viewmodel.MainNavViewModel

class MainAddressBook{
    companion object {
        const val HOME = "home"
        const val HISTORY = "history"
        const val LIST = "list"
        const val ABOUT = "about"
        const val SETTINGS = "settings"
        const val CAMERA = "camera"
        const val MAP = "map"
        const val ACCOUNT = "account"
        fun addMainGraph(navGraphBuilder: NavGraphBuilder,navController: NavController,mainNavViewModel: MainNavViewModel){
            navGraphBuilder.composable(MainAddressBook.HOME){
                HomeScreen(navController,mainNavViewModel.currentLocation.value)
            }
            navGraphBuilder.composable(MainAddressBook.LIST){
                ListSnapScreen()
            }
            navGraphBuilder.composable(MainAddressBook.HISTORY){

            }
            navGraphBuilder.composable(MainAddressBook.ACCOUNT){
                AccountScreen()

            }
            navGraphBuilder.composable(MainAddressBook.SETTINGS){

            }
            navGraphBuilder.composable(MainAddressBook.ABOUT){
                AboutUsScreen()
            }
            navGraphBuilder.composable(MainAddressBook.CAMERA){
                CameraScreen()
            }
            navGraphBuilder.composable(MainAddressBook.MAP){
                MapScreen(navController = navController,mainNavViewModel.currentLocation.value)
            }
        }
    }
}