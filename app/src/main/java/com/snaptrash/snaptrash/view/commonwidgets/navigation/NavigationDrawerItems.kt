package com.snaptrash.snaptrash.view.commonwidgets.navigation

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.material3.DrawerState
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.navOptions
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.snaptrash.snaptrash.R
import com.snaptrash.snaptrash.view.navigator.AuthAddressBook
import com.snaptrash.snaptrash.view.navigator.MainAddressBook
import com.snaptrash.snaptrash.view.commonwidgets.NavigationItem
import kotlinx.coroutines.launch

@Composable
fun NavigationDrawerItems(navController: NavHostController, drawerState: DrawerState){
    var scope = rememberCoroutineScope()

    var currentBackStackEntryAsState = navController.currentBackStackEntryAsState()

    var destination = currentBackStackEntryAsState.value?.destination

    val primaryColorTrasparent = MaterialTheme.colorScheme.primary.copy(alpha = 0.7f)
    val primaryColorTrasparent_L = MaterialTheme.colorScheme.primary.copy(alpha = 0.5f)

    NavigationItem(
        stringResource(R.string.word_map),
        MainAddressBook.HOME,
        destination,
    ) {
        navController.navigate(MainAddressBook.HOME, navOptions {
            this.launchSingleTop = true
            this.restoreState = true
        })
        scope.launch { drawerState.close() }
    }
    Spacer(modifier = Modifier.height(10.dp))
    NavigationItem(
        stringResource(R.string.word_snap_list),
        MainAddressBook.LIST,
        destination,
    ) {
        navController.navigate(MainAddressBook.LIST, navOptions {
            this.launchSingleTop = true
            this.restoreState = true
        })
        scope.launch { drawerState.close() }
    }
    Spacer(modifier = Modifier.height(10.dp))
    NavigationItem(
        stringResource(R.string.word_history),
        MainAddressBook.HISTORY,
        destination,
    ) {
        navController.navigate(MainAddressBook.HISTORY, navOptions {
            this.launchSingleTop = true
            this.restoreState = true
        })
        scope.launch { drawerState.close() }
    }
    Spacer(modifier = Modifier.height(10.dp))

    NavigationItem(
        stringResource(R.string.word_logout),
        AuthAddressBook.LOGIN,
        destination,
    ) {
        Firebase.auth.signOut()
        scope.launch { drawerState.close() }
    }
    Spacer(modifier = Modifier.height(10.dp))

    NavigationItem(
        stringResource(R.string.word_settings),
        MainAddressBook.SETTINGS,
        destination,
    ) {
        navController.navigate(MainAddressBook.SETTINGS, navOptions {
            this.launchSingleTop = true
            this.restoreState = true
        })
        scope.launch { drawerState.close() }
    }
    Spacer(modifier = Modifier.height(10.dp))
    NavigationItem(
        stringResource(R.string.word_about),
        MainAddressBook.ABOUT,
        destination,
    ) {
        navController.navigate(MainAddressBook.ABOUT, navOptions {
            this.launchSingleTop = true
            this.restoreState = true
        })
        scope.launch { drawerState.close() }
    }
    Spacer(modifier = Modifier.height(100.dp))
    //FlagIconWithArrow()
}