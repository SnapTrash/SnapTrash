package com.snaptrash.snaptrash.view.navigator

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.snaptrash.snaptrash.view.HomeScreen.HomeScreen
import com.snaptrash.snaptrash.view.screens.AboutUsScreen
import com.snaptrash.snaptrash.view.screens.ListSnapScreen

class MainAddressBook{
    companion object {
        const val HOME = "home"
        const val HISTORY = "history"
        const val LIST = "list"
        const val ABOUT = "about"
        const val SETTINGS = "settings"
        const val CAMERA = "camera"
        fun addMainGraph(navGraphBuilder: NavGraphBuilder){
            navGraphBuilder.composable(MainAddressBook.HOME){
                HomeScreen()
            }
            navGraphBuilder.composable(MainAddressBook.LIST){
                ListSnapScreen()
            }
            navGraphBuilder.composable(MainAddressBook.HISTORY){

            }
            navGraphBuilder.composable(MainAddressBook.SETTINGS){

            }
            navGraphBuilder.composable(MainAddressBook.ABOUT){
                AboutUsScreen()
            }
            navGraphBuilder.composable(MainAddressBook.CAMERA){
            }
        }
    }
}