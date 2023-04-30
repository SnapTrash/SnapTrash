package com.snaptrash.snaptrash.view.commonwidgets

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.material3.NavigationDrawerItemDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.NavDestination
import androidx.navigation.navOptions
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@Composable
fun NavigationItem(text: String,icon: @Composable () -> Unit,route: String,destination: NavDestination?,onClick: () -> Unit){
    NavigationDrawerItem(
        label = { Text(text = text, fontSize = 18.sp, fontWeight = FontWeight.SemiBold ) },
        selected = destination?.route == route,
        icon = icon,
        onClick = onClick,
        modifier = Modifier.border(1.dp,MaterialTheme.colorScheme.secondaryContainer, RoundedCornerShape(40.dp)) ,
        shape = RoundedCornerShape(40.dp)
    )
}