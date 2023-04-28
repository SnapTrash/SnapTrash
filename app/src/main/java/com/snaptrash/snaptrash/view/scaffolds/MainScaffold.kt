package com.snaptrash.snaptrash.view.scaffolds

import android.content.Context
import android.content.pm.PackageManager
import android.location.LocationListener
import android.location.LocationManager
import android.os.Build
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
fun MainScaffold(navController: NavHostController, vm: MainNavViewModel = viewModel()
) {
    val coroutineScope = rememberCoroutineScope()
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val currentBackStack = navController.currentBackStackEntryAsState()
    val context = LocalContext.current
    if(ActivityCompat.checkSelfPermission(context,android.Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED){
        val locationManager = context.getSystemService(Context.LOCATION_SERVICE) as LocationManager
        locationManager.requestLocationUpdates(if (Build.VERSION.SDK_INT > 30) LocationManager.FUSED_PROVIDER else LocationManager.GPS_PROVIDER,5000,0.0f,
            LocationListener { location ->
                vm.currentLocation.value = GeoPoint(location.latitude,location.longitude)
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
                    containerColor = MaterialTheme.colorScheme.primaryContainer,
                    navigationIconContentColor = MaterialTheme.colorScheme.onPrimaryContainer,
                    titleContentColor = MaterialTheme.colorScheme.onPrimaryContainer

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
                    MainAddressBook.addMainGraph(this,navController,vm)
                }
            }
        }
    }
}

