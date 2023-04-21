package com.snaptrash.snaptrash.view.commonwidgets.navigation

import androidx.appcompat.app.AppCompatDelegate
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Help
import androidx.compose.material.icons.outlined.History
import androidx.compose.material.icons.outlined.List
import androidx.compose.material.icons.outlined.Logout
import androidx.compose.material.icons.outlined.Map
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material.icons.outlined.Settings
import androidx.compose.material3.DrawerState
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.core.os.LocaleListCompat
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.navOptions
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.snaptrash.snaptrash.R
import com.snaptrash.snaptrash.view.commonwidgets.LogoutDialog
import com.snaptrash.snaptrash.view.navigator.AuthAddressBook
import com.snaptrash.snaptrash.view.navigator.MainAddressBook
import com.snaptrash.snaptrash.view.commonwidgets.NavigationItem
import com.snaptrash.snaptrash.view.getActivity
import com.snaptrash.snaptrash.view.getCurrentLocaleIcon
import com.snaptrash.snaptrash.view.screens.DeleteDialog
import kotlinx.coroutines.launch

@Composable
fun NavigationDrawerItems(navController: NavHostController, drawerState: DrawerState){
    var scope = rememberCoroutineScope()

    var currentBackStackEntryAsState = navController.currentBackStackEntryAsState()

    var destination = currentBackStackEntryAsState.value?.destination
    var langMenuExpanded = remember{ mutableStateOf(false)}

    val primaryColorTrasparent = MaterialTheme.colorScheme.primary.copy(alpha = 0.7f)
    val primaryColorTrasparent_L = MaterialTheme.colorScheme.primary.copy(alpha = 0.5f)

    var showLogoutDialog : MutableState<Boolean> = remember { mutableStateOf(false) }


    NavigationItem(
        stringResource(R.string.word_map),
        { Icon(Icons.Outlined.Map,"") },
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
        { Icon(Icons.Outlined.List,"") },
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
        { Icon(Icons.Outlined.History,"") },
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
        stringResource(R.string.account),
        { Icon(Icons.Outlined.Person,"") },
        MainAddressBook.ACCOUNT,
        destination,
    ) {
        navController.navigate(MainAddressBook.ACCOUNT, navOptions {
            this.launchSingleTop = true
            this.restoreState = true
        })
        scope.launch { drawerState.close() }
    }
    Spacer(modifier = Modifier.height(10.dp))
    NavigationItem(
        stringResource(R.string.word_about),
        { Icon(Icons.Outlined.Help,"") },
        MainAddressBook.ABOUT,
        destination,
    ) {
        navController.navigate(MainAddressBook.ABOUT, navOptions {
            this.launchSingleTop = true
            this.restoreState = true
        })
        scope.launch { drawerState.close() }
    }
    Spacer(modifier = Modifier.height(10.dp))

    NavigationItem(
        stringResource(R.string.word_logout),
        { Icon(Icons.Outlined.Logout,"") },
        AuthAddressBook.LOGIN,
        destination,
        onClick= {
        showLogoutDialog.value = true
        scope.launch { drawerState.close() }
    })
    if (showLogoutDialog.value) {
        LogoutDialog(onDismiss = {showLogoutDialog.value = false})
    }

    Spacer(modifier = Modifier.height(10.dp))
    Row(modifier = Modifier
        .fillMaxWidth()
        .padding(start = 10.dp)){
        LocalContext.current.getActivity()?.let { getCurrentLocaleIcon(it) }?.let {
            FlagIconWithArrow(it,langMenuExpanded.value,onClick = {
                langMenuExpanded.value = !langMenuExpanded.value
            })
        }
        DropdownMenu(expanded = langMenuExpanded.value, onDismissRequest = {langMenuExpanded.value = false }) {
            DropdownMenuItem(text = {Text("English")}, onClick = {
                AppCompatDelegate.setApplicationLocales(
                    LocaleListCompat.forLanguageTags("en"))})
            DropdownMenuItem(text = {Text("Français")}, onClick = {
                AppCompatDelegate.setApplicationLocales(
                    LocaleListCompat.forLanguageTags("fr"))})
            DropdownMenuItem(text = {Text("Deutsch")}, onClick = {
                AppCompatDelegate.setApplicationLocales(
                    LocaleListCompat.forLanguageTags("de"))})
            DropdownMenuItem(text = {Text("Magyar")}, onClick = {
                AppCompatDelegate.setApplicationLocales(
                    LocaleListCompat.forLanguageTags("hu"))})
            DropdownMenuItem(text = {Text("Italiano")}, onClick = {
                AppCompatDelegate.setApplicationLocales(
                    LocaleListCompat.forLanguageTags("it"))})
        }
    }
}