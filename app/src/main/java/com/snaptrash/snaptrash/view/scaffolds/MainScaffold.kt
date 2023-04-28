package com.snaptrash.snaptrash.view.scaffolds

import android.content.Context
import android.content.pm.PackageManager
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import android.os.Build
import android.os.Bundle
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.core.app.ActivityCompat
import androidx.core.location.LocationListenerCompat
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.currentBackStackEntryAsState
import com.snaptrash.snaptrash.view.navigator.MainAddressBook
import com.snaptrash.snaptrash.view.commonwidgets.navigation.DrawerContent
import com.snaptrash.snaptrash.viewmodel.MainNavViewModel
import kotlinx.coroutines.launch
import org.osmdroid.util.GeoPoint

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScaffold(
    navController: NavHostController, vm: MainNavViewModel = viewModel()
) {
    val coroutineScope = rememberCoroutineScope()
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val currentBackStack = navController.currentBackStackEntryAsState()
    val context = LocalContext.current
    val locationProvider =
        if (Build.VERSION.SDK_INT > 30) LocationManager.FUSED_PROVIDER else LocationManager.GPS_PROVIDER
    if (ActivityCompat.checkSelfPermission(
            context,
            android.Manifest.permission.ACCESS_FINE_LOCATION
        ) == PackageManager.PERMISSION_GRANTED
    ) {
        val locationManager = context.getSystemService(Context.LOCATION_SERVICE) as LocationManager
        locationManager.requestLocationUpdates(locationProvider, 5000, 0.0f,
            object : LocationListenerCompat {
                override fun onLocationChanged(location: Location) {
                    vm.currentLocation.value = GeoPoint(location.latitude, location.longitude)
                }

                override fun onProviderDisabled(provider: String) {
                    if (provider == locationProvider) {
                        vm.locationEnabled.value = false
                    }
                }

                override fun onProviderEnabled(provider: String) {
                    if (provider == locationProvider) {
                        vm.locationEnabled.value = true
                    }
                }
            })
    }
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
        floatingActionButton = {
            if(currentBackStack.value?.destination?.route?.contains(MainAddressBook.SINGLE_SNAP) == true){
                vm.currentFloatingActionButton.value()
            }
        }
    ) {
        ModalNavigationDrawer(
            gesturesEnabled = currentBackStack.value?.destination?.route != MainAddressBook.MAP,
            drawerContent = {
                DrawerContent(
                    navController = navController,
                    drawerState = drawerState,
                    paddingValues = it
                )
            },
            drawerState = drawerState,
        ) {
            NavHost(
                navController = navController,
                startDestination = MainAddressBook.HOME,
                modifier = Modifier.padding(it)

            ) {
                MainAddressBook.addMainGraph(this, navController, vm)
            }
        }
    }
}

