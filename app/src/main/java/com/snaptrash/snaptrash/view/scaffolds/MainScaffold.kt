package com.snaptrash.snaptrash.view.scaffolds

import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.currentBackStackEntryAsState
import com.snaptrash.snaptrash.view.navigator.MainAddressBook
import com.snaptrash.snaptrash.view.commonwidgets.navigation.DrawerContent
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScaffold(navController: NavHostController
) {
    val coroutineScope = rememberCoroutineScope()
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val currentBackStack = navController.currentBackStackEntryAsState()
    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Text(
                        "SnapTrash",
                        maxLines = 1,
                        style = MaterialTheme.typography.titleLarge,
                        overflow = TextOverflow.Ellipsis,
                    )
                },
                colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primary,
                    navigationIconContentColor = MaterialTheme.colorScheme.background,
                    titleContentColor = MaterialTheme.colorScheme.background

                ),
                navigationIcon = {
                    IconButton(onClick = {
                        if (drawerState.isClosed) {
                            coroutineScope.launch {
                                drawerState.open()
                            }

                        } else {
                            coroutineScope.launch {
                                drawerState.close()
                            }
                        }
                    }) {
                        Icon(
                            imageVector = Icons.Filled.Menu,
                            contentDescription = "Localized description",
                            modifier = Modifier
                                .size(36.dp)
                        )
                    }
                },
            )
        },
    ){
        ModalNavigationDrawer(
            gesturesEnabled = currentBackStack.value?.destination?.route != MainAddressBook.MAP,
            drawerContent = {
                DrawerContent(
                    navController = navController,
                    drawerState =drawerState,
                    paddingValues = it) },
            drawerState = drawerState,
        ) {
            Box(modifier = Modifier.padding(it)) {
                NavHost(
                    navController = navController,
                    startDestination = MainAddressBook.HOME

                ) {
                    MainAddressBook.addMainGraph(this,navController)
                }
            }
        }
    }
}

