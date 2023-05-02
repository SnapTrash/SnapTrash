package com.snaptrash.snaptrash.view.navigator

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.snaptrash.snaptrash.model.data.Snap
import com.snaptrash.snaptrash.view.HomeScreen.HomeScreen
import com.snaptrash.snaptrash.view.commonwidgets.LogoutDialog
import com.snaptrash.snaptrash.view.helper.ConnectionState
import com.snaptrash.snaptrash.view.helper.connectivityState
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
        const val PERSONAL_DETAILS = "personal_details"
        const val CHANGE_PASSWORD = "change_password"
        const val CHANGE_USERNAME = "change_username"
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
                AccountScreen(navController)
            }
            navGraphBuilder.composable(MainAddressBook.PERSONAL_DETAILS){
                PersonalDetailsScreen(navController)
            }
            navGraphBuilder.composable(MainAddressBook.CHANGE_PASSWORD){
                ChangePasswordScreen()
            }

            navGraphBuilder.composable(MainAddressBook.CHANGE_USERNAME){
                ChangeUserNameScreen()
            }

            navGraphBuilder.composable(MainAddressBook.SETTINGS){

            }
            navGraphBuilder.composable(MainAddressBook.ABOUT){
                AboutUsScreen()
            }
            navGraphBuilder.composable(MainAddressBook.CAMERA){
                val network = connectivityState()
                if(network.value == ConnectionState.Available)
                CameraScreen(navController,mainNavViewModel.currentLocation.value)
                else
                    HomeScreen(navController = navController, mainNavViewModel = mainNavViewModel)
            }
            navGraphBuilder.composable(MainAddressBook.MAP){
                val network = connectivityState()
                if(network.value == ConnectionState.Available)
                MapScreen(navController = navController,mainNavViewModel)
                else
                    HomeScreen(navController = navController, mainNavViewModel = mainNavViewModel)
            }
            navGraphBuilder.composable(MainAddressBook.SINGLE_SNAP){
                val moshi = Moshi.Builder().add(Date::class.java, Rfc3339DateJsonAdapter().nullSafe()).addLast(KotlinJsonAdapterFactory()).build()
                val adapter = moshi.adapter(Snap::class.java)
                val snap = adapter.fromJson(it.arguments?.getString("snap")!!)
                OpenSnapScreen(snap!!,navController,mainNavViewModel, snap.id.isEmpty())
            }
            navGraphBuilder.composable(MainAddressBook.LOGOUT){
                var showLogoutDialog : MutableState<Boolean> = remember { mutableStateOf(true) }
                if (showLogoutDialog.value) {
                    LogoutDialog(onDismiss = {run{
                        showLogoutDialog.value = false
                        navController.popBackStack()
                    }  },
                        { Firebase.auth.signOut() })
                }
            }
        }
    }
}