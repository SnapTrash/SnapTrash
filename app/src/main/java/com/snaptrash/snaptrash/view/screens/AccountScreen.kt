package com.snaptrash.snaptrash.view.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.layout.R
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowRight
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
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
        //horizontalAlignment = Alignment.CenterHorizontally,
    ){
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
                .align(Alignment.CenterHorizontally)

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

        Row(modifier = Modifier
            .padding(horizontal = 40.dp)
            .fillMaxWidth(),
            //horizontalArrangement= Arrangement.End,
            verticalAlignment = Alignment.CenterVertically
        ){
            Text(text = stringResource(id = com.snaptrash.snaptrash.R.string.account),color = MaterialTheme.colorScheme.primary,
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
        Spacer(modifier = Modifier.height(20.dp))

    }




}