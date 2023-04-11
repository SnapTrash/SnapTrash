package com.snaptrash.snaptrash.view.Navigator

import android.annotation.SuppressLint
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.R
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.modifier.modifierLocalConsumer
import androidx.compose.ui.modifier.modifierLocalOf
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navOptions
import com.snaptrash.snaptrash.view.ListSnapScreen.ListSnapScreen
import kotlinx.coroutines.launch



@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopNavigation(
) {
    val navController = rememberNavController()
    val coroutineScope = rememberCoroutineScope()
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)

    //val primaryColorTrasparent = MaterialTheme.colorScheme.primary.copy(alpha = 0.5f)

    ModalNavigationDrawer(drawerContent = {DrawerContent(navController = navController, drawerState =drawerState )}, drawerState = drawerState,
        drawerContainerColor = MaterialTheme.colorScheme.background

    ) {
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
            Box(modifier = Modifier.padding(it)){
                NavHost(
                    navController = navController,
                    startDestination = "Home"

                ){
                    composable("Home"){

                    }
                    composable("Map"){
                    }
                    composable("List"){
                        ListSnapScreen()
                    }
                    composable("History"){

                    }
                    composable("Logout"){

                    }
                    composable("Settings"){

                    }
                    composable("About"){

                    }

                }
            }
        }
    }
}


@OptIn(ExperimentalMaterial3Api::class)
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
        NavigationDrawerItems(navController,drawerState )
        Spacer(modifier = Modifier.height(30.dp))
        Row( horizontalArrangement = Arrangement.Start,  modifier = Modifier.fillMaxWidth())  {

            FlagIconWithArrow()
        }
    }

}


@Composable
fun DrawerHeader(){
    Column(
        modifier = Modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Box(
            modifier = androidx.compose.ui.Modifier
                .fillMaxWidth()
                .height(100.dp)
                .background(color = MaterialTheme.colorScheme.primary)
        ) {
            Text(
                "SnapTrash",
                maxLines = 1,
                style = MaterialTheme.typography.titleLarge,
                color = MaterialTheme.colorScheme.background,
                overflow = TextOverflow.Ellipsis,
                modifier = androidx.compose.ui.Modifier
                    .align(Alignment.Center)
            )
        }
        // altri composable qui...
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NavigationDrawerItems(navController: NavHostController, drawerState: DrawerState){
    var scope = rememberCoroutineScope()

    var currentBackStackEntryAsState = navController.currentBackStackEntryAsState()

    var destination = currentBackStackEntryAsState.value?.destination

    val primaryColorTrasparent = MaterialTheme.colorScheme.primary.copy(alpha = 0.7f)
    val primaryColorTrasparent_L = MaterialTheme.colorScheme.primary.copy(alpha = 0.5f)

    NavigationDrawerItem(
        label = {Text(text = "Map", color = MaterialTheme.colorScheme.secondary, fontSize = 24.sp, fontWeight = FontWeight.SemiBold )},
        shape= RectangleShape ,
        selected = destination?.route == "Map",
        colors = NavigationDrawerItemDefaults.colors(unselectedContainerColor= primaryColorTrasparent,
            selectedContainerColor = primaryColorTrasparent_L),
        onClick = { navController.navigate("Map", navOptions{
            this.launchSingleTop = true
            this.restoreState = true
        })
            scope.launch { drawerState.close() }
        },
        modifier = Modifier
            .fillMaxWidth()
            .border(1.dp, MaterialTheme.colorScheme.secondary, RectangleShape)

    )
    Spacer(modifier = Modifier.height(10.dp))

    NavigationDrawerItem(
        label = {Text(text = "List", color = MaterialTheme.colorScheme.secondary, fontSize = 24.sp, fontWeight = FontWeight.SemiBold )},
        shape= RectangleShape ,
        selected = destination?.route == "List",
        colors = NavigationDrawerItemDefaults.colors(unselectedContainerColor= primaryColorTrasparent,
            selectedContainerColor = primaryColorTrasparent_L),
        onClick = { navController.navigate("List", navOptions{
            this.launchSingleTop = true
            this.restoreState = true
        })
            scope.launch { drawerState.close() }
        },
        modifier = Modifier
            .fillMaxWidth()
            .border(1.dp, MaterialTheme.colorScheme.secondary, RectangleShape)

    )


    Spacer(modifier = Modifier.height(10.dp))

    NavigationDrawerItem(
        label = {Text(text = "History", color = MaterialTheme.colorScheme.secondary, fontSize = 24.sp, fontWeight = FontWeight.SemiBold )},
        shape= RectangleShape ,
        selected = destination?.route == "History",
        colors = NavigationDrawerItemDefaults.colors(unselectedContainerColor= primaryColorTrasparent,
            selectedContainerColor = primaryColorTrasparent_L),
        onClick = { navController.navigate("History", navOptions{
            this.launchSingleTop = true
            this.restoreState = true
        })
            scope.launch { drawerState.close() }
        },
        modifier = Modifier
            .fillMaxWidth()
            .border(1.dp, MaterialTheme.colorScheme.secondary, RectangleShape)

    )
    Spacer(modifier = Modifier.height(10.dp))

    NavigationDrawerItem(
        label = {Text(text = "Logout", color = MaterialTheme.colorScheme.secondary, fontSize = 24.sp, fontWeight = FontWeight.SemiBold )},
        shape= RectangleShape ,
        selected = destination?.route == "Logout",
        colors = NavigationDrawerItemDefaults.colors(unselectedContainerColor= primaryColorTrasparent,
            selectedContainerColor = primaryColorTrasparent_L),
        onClick = { navController.navigate("Logout", navOptions{
            this.launchSingleTop = true
            this.restoreState = true
        })
            scope.launch { drawerState.close() }
        },
        modifier = Modifier
            .fillMaxWidth()
            .border(1.dp, MaterialTheme.colorScheme.secondary, RectangleShape)

    )
    Spacer(modifier = Modifier.height(10.dp))

    NavigationDrawerItem(
        label = {Text(text = "Settings", color = MaterialTheme.colorScheme.secondary, fontSize = 24.sp, fontWeight = FontWeight.SemiBold )},
        shape= RectangleShape ,
        selected = destination?.route == "Settings",
        colors = NavigationDrawerItemDefaults.colors(unselectedContainerColor= primaryColorTrasparent,
            selectedContainerColor = primaryColorTrasparent_L),
        onClick = { navController.navigate("Settings", navOptions{
            this.launchSingleTop = true
            this.restoreState = true
        })
            scope.launch { drawerState.close() }
        },
        modifier = Modifier
            .fillMaxWidth()
            .border(1.dp, MaterialTheme.colorScheme.secondary, RectangleShape)

    )
    Spacer(modifier = Modifier.height(10.dp))

    NavigationDrawerItem(
        label = {Text(text = "About", color = MaterialTheme.colorScheme.secondary, fontSize = 24.sp, fontWeight = FontWeight.SemiBold )},
        shape= RectangleShape ,
        selected = destination?.route == "About",
        colors = NavigationDrawerItemDefaults.colors(unselectedContainerColor= primaryColorTrasparent,
            selectedContainerColor = primaryColorTrasparent_L),
        onClick = { navController.navigate("About", navOptions{
            this.launchSingleTop = true
            this.restoreState = true
        })
            scope.launch { drawerState.close() }
        },
        modifier = Modifier
            .fillMaxWidth()
            .border(1.dp, MaterialTheme.colorScheme.secondary, RectangleShape)

    )
    Spacer(modifier = Modifier.height(100.dp))
    //FlagIconWithArrow()


}




@Composable
fun FlagIconWithArrow() {
    var arrowDirection by remember { mutableStateOf(false) }
    Row {
        Box(
            Modifier
                .padding(8.dp)
                .size(48.dp)
                .clickable(onClick = {
                    arrowDirection = !arrowDirection
                    /* Navigate to the "Language" screen */
                }),
            contentAlignment = Alignment.Center
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Image(
                    painter = painterResource(id = com.snaptrash.snaptrash.R.drawable.united_kingdom),
                    contentDescription = "Flag of United States",
                    Modifier
                        .size(32.dp)
                )
                if (arrowDirection) {
                    Icon(
                        Icons.Filled.KeyboardArrowUp,
                        contentDescription = "Arrow",
                        Modifier.size(24.dp)
                    )
                } else {
                    Icon(
                        Icons.Filled.KeyboardArrowDown,
                        contentDescription = "Arrow",
                        Modifier.size(24.dp)
                    )
                }
            }

        }
    }
}
