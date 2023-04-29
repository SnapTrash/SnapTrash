package com.snaptrash.snaptrash.view.HomeScreen

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.snaptrash.snaptrash.R
import com.snaptrash.snaptrash.view.commonwidgets.map.MapView
import com.snaptrash.snaptrash.view.navigator.MainAddressBook
import com.snaptrash.snaptrash.viewmodel.MainNavViewModel
import org.osmdroid.util.GeoPoint
import org.osmdroid.views.overlay.Marker


@SuppressLint("ClickableViewAccessibility")
@Composable
fun HomeScreen(navController: NavController,mainNavViewModel: MainNavViewModel) {
    val configuration = LocalConfiguration.current
    Column(
        modifier = Modifier.padding(20.dp),
    ) {
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
                mainNavViewModel.snapList.forEach { snap ->
                    val marker: Marker = Marker(it)
                    marker.position = GeoPoint(snap.location.latitude, snap.location.longitude)
                    marker.setAnchor(Marker.ANCHOR_CENTER, Marker.ANCHOR_BOTTOM)
                    it.overlays.add(marker)
                }
                it.setOnTouchListener { v, e ->
                    run {
                        if(navController.currentBackStackEntry?.destination?.route != MainAddressBook.MAP)
                            navController.navigate(MainAddressBook.MAP)
                        true
                    }
                }
            }
        }
        Spacer(modifier = Modifier.height(30.dp))
        Button(
            onClick = {
                navController.navigate(MainAddressBook.CAMERA)
            },
            modifier = Modifier
                .fillMaxWidth()
                .height(70.dp)

        ) {
            Text(
                //text = "Submit",
                text = stringResource(R.string.instruction_take_a_snap),
                fontSize = 24.sp,
                textAlign = TextAlign.Center,
                modifier = Modifier.fillMaxWidth(),
                color = MaterialTheme.colorScheme.onPrimaryContainer
            )
        }

    }


}