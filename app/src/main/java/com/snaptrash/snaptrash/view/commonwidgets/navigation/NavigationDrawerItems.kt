package com.snaptrash.snaptrash.view.commonwidgets.navigation

import androidx.appcompat.app.AppCompatDelegate
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Help
import androidx.compose.material.icons.outlined.History
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.List
import androidx.compose.material.icons.outlined.Logout
import androidx.compose.material.icons.outlined.Map
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material.icons.outlined.QuestionMark
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
import com.snaptrash.snaptrash.R
import com.snaptrash.snaptrash.view.commonwidgets.LogoutDialog
import com.snaptrash.snaptrash.view.navigator.AuthAddressBook
import com.snaptrash.snaptrash.view.navigator.MainAddressBook
import com.snaptrash.snaptrash.view.commonwidgets.NavigationItem
import com.snaptrash.snaptrash.view.getActivity
import com.snaptrash.snaptrash.view.getCurrentLocaleIcon
import com.snaptrash.snaptrash.view.helper.ConnectionState
import com.snaptrash.snaptrash.view.helper.connectivityState
import kotlinx.coroutines.launch

@Composable
fun NavigationDrawerItems(navController: NavHostController, drawerState: DrawerState){
    val scope = rememberCoroutineScope()

    val currentBackStackEntryAsState = navController.currentBackStackEntryAsState()
    val networkState = connectivityState()
    val destination = currentBackStackEntryAsState.value?.destination
    val langMenuExpanded = remember{ mutableStateOf(false)}

    val primaryColorTrasparent = MaterialTheme.colorScheme.primary.copy(alpha = 0.7f)
    val primaryColorTrasparent_L = MaterialTheme.colorScheme.primary.copy(alpha = 0.5f)

    val showLogoutDialog : MutableState<Boolean> = remember { mutableStateOf(false) }
    val navigationDestinations = mutableListOf(
        NavMenuPoint(MainAddressBook.HOME,Icons.Outlined.Home, stringResource(R.string.word_home_screen)),
        NavMenuPoint(MainAddressBook.LIST,Icons.Outlined.List, stringResource(R.string.word_snap_list)),
        NavMenuPoint(MainAddressBook.HISTORY,Icons.Outlined.History, stringResource(R.string.word_history)),
        NavMenuPoint(MainAddressBook.ACCOUNT,Icons.Outlined.Person, stringResource(R.string.account)),
        NavMenuPoint(MainAddressBook.ABOUT,Icons.Outlined.Help, stringResource(id = R.string.word_about)),
        NavMenuPoint(MainAddressBook.LOGOUT,Icons.Outlined.Logout, stringResource(id = R.string.word_logout))
    )
    if(networkState.value == ConnectionState.Available)
        navigationDestinations.add(1,NavMenuPoint(MainAddressBook.MAP,Icons.Outlined.Map, stringResource(R.string.word_map)))
    val selectableLanguages = mapOf(
        "en" to "English",
        "de" to "Deutsch",
        "fi" to "suomi",
        "fr" to "Français",
        "hu" to "magyar",
        "it" to "Italiano")
    Column{
        navigationDestinations.forEach{
                NavigationItem(
                    it.text,
                    { Icon(it.icon, "") },
                    it.route,
                    destination,
                ) {
                    navController.navigate(it.route, navOptions {
                        this.launchSingleTop = true
                        this.restoreState = true
                    })
                    scope.launch { drawerState.close() }
                }
                Spacer(Modifier.height(10.dp))
        }
    }
    Row(modifier = Modifier
        .fillMaxWidth()
        .padding(start = 10.dp)){
        LocalContext.current.getActivity()?.let { getCurrentLocaleIcon(it) }?.let {
            FlagIconWithArrow(it,langMenuExpanded.value,onClick = {
                langMenuExpanded.value = !langMenuExpanded.value
            })
        }
        DropdownMenu(expanded = langMenuExpanded.value, onDismissRequest = {langMenuExpanded.value = false }) {
            selectableLanguages.forEach {
                DropdownMenuItem(text = { Text(it.value) }, onClick = {
                    AppCompatDelegate.setApplicationLocales(
                        LocaleListCompat.forLanguageTags(it.key)
                    )
                })
            }
        }
    }
}