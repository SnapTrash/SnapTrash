package com.snaptrash.snaptrash.view.navigator

import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.snaptrash.snaptrash.view.scaffolds.MainScaffold
import com.snaptrash.snaptrash.viewmodel.RootNavViewModel

@Composable
fun RootNav(navController: NavHostController,vm: RootNavViewModel = viewModel()){
    if(vm.isLoggedIn.value){
        MainScaffold(navController)
    }
    else{
        LoginNavigation(navController)
    }
}