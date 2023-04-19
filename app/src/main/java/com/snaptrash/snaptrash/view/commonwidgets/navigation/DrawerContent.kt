package com.snaptrash.snaptrash.view.commonwidgets.navigation

import androidx.compose.foundation.layout.*
import androidx.compose.material3.Divider
import androidx.compose.material3.DrawerState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.snaptrash.snaptrash.R

@Composable
fun DrawerContent(navController: NavHostController, drawerState: DrawerState){
    Column(
        modifier = Modifier
            .fillMaxSize()
        ,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        DrawerHeader()
        Spacer(modifier = Modifier.height(10.dp))
        Divider(color = Color.LightGray)
        Spacer(modifier = Modifier.height(10.dp))
        NavigationDrawerItems(navController,drawerState)
        Spacer(modifier = Modifier.height(30.dp))
        Row( horizontalArrangement = Arrangement.Start,  modifier = Modifier.fillMaxWidth())  {
            FlagIconWithArrow(R.drawable.united_kingdom)
        }
    }

}