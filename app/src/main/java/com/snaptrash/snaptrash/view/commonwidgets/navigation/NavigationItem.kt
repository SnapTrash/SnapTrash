package com.snaptrash.snaptrash.view.commonwidgets

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.fillMaxWidth
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
    val primaryColorTrasparent = MaterialTheme.colorScheme.primary.copy(alpha = 0.7f)
    val primaryColorTrasparent_L = MaterialTheme.colorScheme.primary.copy(alpha = 0.5f)
    NavigationDrawerItem(
        label = { Text(text = text, color = MaterialTheme.colorScheme.secondary, fontSize = 20.sp, fontWeight = FontWeight.SemiBold ) },
        //shape= RectangleShape ,
        selected = destination?.route == route,
        icon = icon,
        /*colors = NavigationDrawerItemDefaults.colors(unselectedContainerColor= primaryColorTrasparent,
            selectedContainerColor = primaryColorTrasparent_L),*/
        onClick = onClick,
        /*modifier = Modifier
            .border(1.dp, MaterialTheme.colorScheme.secondary, RectangleShape)*/

    )
}