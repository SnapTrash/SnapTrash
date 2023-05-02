package com.snaptrash.snaptrash.view.commonwidgets.navigation

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredWidth
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Divider
import androidx.compose.material3.DrawerState
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.snaptrash.snaptrash.viewmodel.MainNavViewModel

@Composable
fun DrawerContent(navController: NavHostController, drawerState: DrawerState,paddingValues: PaddingValues,displayName: String,mainNavViewModel: MainNavViewModel){
    val scrollState = rememberScrollState()
    val configuration = LocalConfiguration.current
    Column(
        modifier = Modifier
        .fillMaxHeight()
        .requiredWidth((configuration.screenWidthDp * 0.85).dp)
        .padding(paddingValues)
        //.padding(top = 1.dp, bottom = 5.dp) //changes
        .background(MaterialTheme.colorScheme.background)
    ){
        DrawerHeader(displayName,mainNavViewModel)
        Column(
            modifier = Modifier
                .padding(5.dp)
                .verticalScroll(scrollState)
            ,
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Spacer(modifier = Modifier.height(10.dp))
            NavigationDrawerItems(navController,drawerState)
            Spacer(modifier = Modifier.height(10.dp))
        }
    }
}