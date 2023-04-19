package com.snaptrash.snaptrash.view.Navigator

import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.snaptrash.snaptrash.viewmodel.RootNavViewModel

@Composable
fun RootNav(navController: NavHostController,vm: RootNavViewModel = viewModel()){
    if(vm.isLoggedIn.value){
        TopNavigation(navController)
    }
    else{
        LoginNavigation(navController)
    }
}