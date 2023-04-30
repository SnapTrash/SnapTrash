package com.snaptrash.snaptrash.view.navigator

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.snaptrash.snaptrash.model.data.Snap
import com.snaptrash.snaptrash.view.HomeScreen.HomeScreen
import com.snaptrash.snaptrash.view.screens.*
import com.snaptrash.snaptrash.viewmodel.MainNavViewModel
import com.squareup.moshi.Moshi
import com.squareup.moshi.adapters.Rfc3339DateJsonAdapter
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import java.util.Date

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
        const val LOGOUT = "logout"
        const val SINGLE_SNAP = "snap/snap={snap}"
        fun addMainGraph(navGraphBuilder: NavGraphBuilder,navController: NavController,mainNavViewModel: MainNavViewModel){
            navGraphBuilder.composable(MainAddressBook.HOME){
                HomeScreen(navController,mainNavViewModel)
            }
            navGraphBuilder.composable(MainAddressBook.LIST){
                ListSnapScreen(mainNavViewModel.snapList,navController,false)
            }
            navGraphBuilder.composable(MainAddressBook.HISTORY){
                ListSnapScreen(mainNavViewModel.snapList,navController,true)
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
                CameraScreen(navController,mainNavViewModel.currentLocation.value)
            }
            navGraphBuilder.composable(MainAddressBook.MAP){
                MapScreen(navController = navController,mainNavViewModel)
            }
            navGraphBuilder.composable(MainAddressBook.SINGLE_SNAP){
                val moshi = Moshi.Builder().add(Date::class.java, Rfc3339DateJsonAdapter().nullSafe()).addLast(KotlinJsonAdapterFactory()).build()
                val adapter = moshi.adapter(Snap::class.java)
                val snap = adapter.fromJson(it.arguments?.getString("snap")!!)
                OpenSnapScreen(snap!!,navController,mainNavViewModel, snap.id.isEmpty())
            }
            navGraphBuilder.composable(MainAddressBook.LOGOUT){
                Firebase.auth.signOut()
            }
        }
    }
}