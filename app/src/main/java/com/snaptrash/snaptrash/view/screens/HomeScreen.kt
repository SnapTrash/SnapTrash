package com.snaptrash.snaptrash.view.HomeScreen

import android.annotation.SuppressLint
import android.content.Intent
import android.graphics.drawable.BitmapDrawable
import android.provider.Settings
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.content.res.ResourcesCompat
import androidx.core.graphics.scale
import androidx.navigation.NavController
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.isGranted
import com.google.accompanist.permissions.rememberPermissionState
import com.snaptrash.snaptrash.R
import com.snaptrash.snaptrash.model.data.SnapStatus
import com.snaptrash.snaptrash.view.MainActivity
import com.snaptrash.snaptrash.view.commonwidgets.ErrorCard
import com.snaptrash.snaptrash.view.commonwidgets.map.MapView
import com.snaptrash.snaptrash.view.helper.ConnectionState
import com.snaptrash.snaptrash.view.helper.MapHelper
import com.snaptrash.snaptrash.view.helper.connectivityState
import com.snaptrash.snaptrash.view.navigator.MainAddressBook
import com.snaptrash.snaptrash.viewmodel.MainNavViewModel
import org.osmdroid.util.GeoPoint
import org.osmdroid.views.overlay.Marker


@OptIn(ExperimentalPermissionsApi::class)
@SuppressLint("ClickableViewAccessibility")
@Composable
fun HomeScreen(navController: NavController,mainNavViewModel: MainNavViewModel) {
    val context = LocalContext.current
    val configuration = LocalConfiguration.current
    val cameraPermission = rememberPermissionState(android.Manifest.permission.CAMERA)
    val locationEnabled = rememberPermissionState(permission = android.Manifest.permission.ACCESS_FINE_LOCATION)
    val connectivity = connectivityState()
    Column(
        modifier = Modifier.padding(20.dp),
    ) {
        if(connectivity.value != ConnectionState.Available){
            ErrorCard(stringResource(R.string.error_home_no_network))
            Spacer(Modifier.height(20.dp))
        }
        Box(
            Modifier
                .height((configuration.screenHeightDp * 0.6).dp)
                .fillMaxWidth()
                .align(Alignment.CenterHorizontally)
                //.aspectRatio(1.0f) // fixed aspect ratio could be usefull to resize the map
                .border(
                    width = 2.dp,
                    color = MaterialTheme.colorScheme.primary,
                )
                .background(color = Color.White)
        ) {
            MapView(
                modifier = Modifier.fillMaxSize()
            ) {
                it.controller.setZoom(14.0)
                it.controller.setCenter(mainNavViewModel.currentLocation.value)
                MapHelper.addSnapsToMap(context, configuration, it, mainNavViewModel.snapList)
                it.setOnTouchListener { v, e ->
                    run {
                        if (connectivity.value == ConnectionState.Available) {
                            if (navController.currentBackStackEntry?.destination?.route != MainAddressBook.MAP)
                                navController.navigate(MainAddressBook.MAP)
                        }
                        true
                    }
                }
            }
        }
        Spacer(modifier = Modifier.height(30.dp))
        if (connectivity.value == ConnectionState.Available) {
            if (cameraPermission.status.isGranted && locationEnabled.status.isGranted && mainNavViewModel.locationEnabled.value) {
                Button(
                    onClick = {
                        navController.navigate(MainAddressBook.CAMERA)
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(70.dp)

                ) {
                    Text(
                        text = stringResource(R.string.instruction_take_a_snap),
                        fontSize = 24.sp,
                        textAlign = TextAlign.Center,
                        modifier = Modifier.fillMaxWidth(),
                        color = MaterialTheme.colorScheme.onPrimaryContainer
                    )
                }
            } else {
                Button(
                    {
                        (context as MainActivity).requestMissingPermissions()
                        if(locationEnabled.status.isGranted && cameraPermission.status.isGranted){
                            val intent = Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS)
                            context.startActivity(intent)
                        }
                        mainNavViewModel.checkPermissions(context)
                    },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = MaterialTheme.colorScheme.errorContainer,
                        contentColor = MaterialTheme.colorScheme.onErrorContainer
                    ),
                    modifier = Modifier
                        .fillMaxWidth()
                ) {
                    Text(
                        if(!locationEnabled.status.isGranted ||
                                !cameraPermission.status.isGranted)
                            stringResource(R.string.button_request_missing_permissions)
                        else stringResource(R.string.instruction_turn_on_location),
                        fontSize = 24.sp,
                        lineHeight = 27.sp,
                        textAlign = TextAlign.Center,
                        modifier = Modifier.fillMaxWidth().padding(5.dp)
                    )
                }
            }

        }
    }


}