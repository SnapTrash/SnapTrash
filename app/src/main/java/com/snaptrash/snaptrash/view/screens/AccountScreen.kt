package com.snaptrash.snaptrash.view.screens

import android.graphics.Bitmap
import android.graphics.ImageDecoder
import android.net.Uri
import android.os.Build
import android.provider.MediaStore
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.layout.R
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.modifier.modifierLocalConsumer
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImagePainter
import coil.compose.rememberAsyncImagePainter
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase


@Composable
fun AccountScreen(){
    val checkStateSwitch = remember { mutableStateOf(false) }
    Column(
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Box(
            Modifier
                .height(160.dp)
                .width(160.dp)
                .padding(15.dp)
                //.aspectRatio(1.0f) // fixed aspect ratio

                .background(Color.Red, CircleShape)

                .border(
                    width = 1.dp,
                    color = MaterialTheme.colorScheme.primary,
                    shape = CircleShape
                )

        ){
            Image(
                modifier = Modifier
                    .fillMaxSize()
                    .clip(shape = CircleShape),
                contentScale = ContentScale.Crop,
                painter =
                    if(Firebase.auth.currentUser?.photoUrl != null)
                        rememberAsyncImagePainter(Firebase.auth.currentUser?.photoUrl)
                    else
                        painterResource(com.snaptrash.snaptrash.R.drawable.profile_image),
                contentDescription = "Profile Image"
            )

        }

        Text(text = Firebase.auth.currentUser?.displayName ?: "Noname",color = MaterialTheme.colorScheme.secondary,
            fontWeight = FontWeight.SemiBold, fontSize = 24.sp,
            modifier = Modifier.align(Alignment.CenterHorizontally))
        Text(text = Firebase.auth.currentUser?.email ?: "invalid",color = MaterialTheme.colorScheme.secondary,
            fontWeight = FontWeight.Medium, fontSize = 20.sp,
            modifier = Modifier.align(Alignment.CenterHorizontally))
        Spacer(modifier = Modifier.height(80.dp))


/*
        Row(modifier = Modifier
            .padding(horizontal = 40.dp)
            .fillMaxWidth(),
            //horizontalArrangement= Arrangement.End,
            verticalAlignment = Alignment.CenterVertically
        ){
            Text(text = stringResource(id = com.snaptrash.snaptrash.R.string.account),
                fontWeight = FontWeight.Normal, fontSize = 26.sp,
                textAlign = TextAlign.Left
            )
            Spacer(modifier = Modifier.width(150.dp))
            Icon(
                Icons.Filled.KeyboardArrowRight,
                contentDescription = "ArrowRight",
                tint = MaterialTheme.colorScheme.primary,
                modifier = Modifier
                    .size(32.dp)
                    .align(Alignment.Bottom)
                    .clickable(onClick = {})

            )
        }


        Row(modifier = Modifier
            .padding(horizontal = 40.dp)
            .fillMaxWidth(),
            //horizontalArrangement= Arrangement.End,
            verticalAlignment = Alignment.CenterVertically
        ){
            Text(text = "Password setting",
                fontWeight = FontWeight.Normal, fontSize = 26.sp,
                textAlign = TextAlign.Left
            )
            Spacer(modifier = Modifier.width(150.dp))
            Icon(
                Icons.Filled.KeyboardArrowRight,
                contentDescription = "ArrowRight",
                tint = MaterialTheme.colorScheme.primary,
                modifier = Modifier
                    .size(32.dp)
                    .align(Alignment.Bottom)
                    .clickable(onClick = {})

            )
        }
        Row(modifier = Modifier
            .padding(horizontal = 40.dp)
            .fillMaxWidth(),
            //horizontalArrangement= Arrangement.End,
            verticalAlignment = Alignment.CenterVertically
        ){
            Text(text = "Personal details",
                fontWeight = FontWeight.Normal, fontSize = 26.sp,
                textAlign = TextAlign.Left
            )
            Spacer(modifier = Modifier.width(150.dp))
            Icon(
                Icons.Filled.KeyboardArrowRight,
                contentDescription = "ArrowRight",
                tint = MaterialTheme.colorScheme.primary,
                modifier = Modifier
                    .size(32.dp)
                    .align(Alignment.Bottom)
                    .clickable(onClick = {})

            )

        }

 */

        ListOptions()


        Spacer(modifier = Modifier.height(20.dp))

    }




}


@Composable
fun ListOptions() {
    val configuration = LocalConfiguration.current
    Column(
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        ListItem(
            modifier = Modifier
                .width((configuration.screenWidthDp * 0.85).dp)
                .clip(RoundedCornerShape(16.dp))
                .align(Alignment.CenterHorizontally)
                //.background(MaterialTheme.colorScheme.onPrimaryContainer, RoundedCornerShape(30.dp)),
                .border(1.dp, MaterialTheme.colorScheme.primary, RoundedCornerShape(16.dp))
                .clickable(onClick = {}), //go to screen to set this information
            headlineContent = { Text("Personal details") },
            leadingContent = {
                Icon(
                    Icons.Filled.Person,
                    contentDescription = "Localized description",
                )
            }
        )
        Spacer(modifier = Modifier.height(5.dp))
        ListItem(
            modifier = Modifier
                .width((configuration.screenWidthDp * 0.85).dp)
                .clip(RoundedCornerShape(16.dp))
                .align(Alignment.CenterHorizontally)
                //.background(MaterialTheme.colorScheme.onPrimaryContainer, RoundedCornerShape(30.dp)),
                .border(1.dp, MaterialTheme.colorScheme.primary, RoundedCornerShape(16.dp))
                .clickable(onClick = {}), //go to screen to set this information
            headlineContent = { Text("Password settings") },
            leadingContent = {
                Icon(
                    Icons.Filled.Password,
                    contentDescription = "Localized description",
                )
            }
        )

        Spacer(modifier = Modifier.height(5.dp))
        ListItem(
            modifier = Modifier
                .width((configuration.screenWidthDp * 0.85).dp)
                .clip(RoundedCornerShape(16.dp))
                .align(Alignment.CenterHorizontally)
                //.background(MaterialTheme.colorScheme.onPrimaryContainer, RoundedCornerShape(30.dp)),
                .border(1.dp, MaterialTheme.colorScheme.primary, RoundedCornerShape(16.dp))
                .clickable(onClick = {}), //go to screen to set this information
            headlineContent = { Text("Notification") },
            leadingContent = {
                Icon(
                    Icons.Filled.Notifications,
                    contentDescription = "Localized description",
                )
            }
        )

        Spacer(modifier = Modifier.height(5.dp))
        ListItem(
            modifier = Modifier
                .width((configuration.screenWidthDp * 0.85).dp)
                .clip(RoundedCornerShape(16.dp))
                .align(Alignment.CenterHorizontally)
                //.background(MaterialTheme.colorScheme.onPrimaryContainer, RoundedCornerShape(30.dp)),
                .border(1.dp, MaterialTheme.colorScheme.primary, RoundedCornerShape(16.dp))
                .clickable(onClick = {}), //go to screen to set this information
            headlineContent = { Text("Payment") },
            leadingContent = {
                Icon(
                    Icons.Filled.CreditCard,
                    contentDescription = "Localized description",
                )
            }
        )
    }
}

