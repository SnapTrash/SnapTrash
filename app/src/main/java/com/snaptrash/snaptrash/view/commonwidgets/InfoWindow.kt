package com.snaptrash.snaptrash.view.commonwidgets

import android.net.Uri
import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*



import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter
import java.net.URLDecoder


@Composable
fun InfoWindow(){
    val configuration = LocalConfiguration.current
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ){

        Box(
            modifier = Modifier.background(MaterialTheme.colorScheme.surfaceVariant)
                .clip(RoundedCornerShape(16.dp))
                .border(3.dp, MaterialTheme.colorScheme.primaryContainer, RoundedCornerShape(16.dp))
                .height((configuration.screenHeightDp * 0.3).dp)
                .width((configuration.screenWidthDp * 0.6).dp)
            ,
            contentAlignment = Alignment.Center

        ){
            Box(modifier = Modifier.background(MaterialTheme.colorScheme.surfaceVariant)
                .clip(RoundedCornerShape(8.dp))
                .border(3.dp, MaterialTheme.colorScheme.primaryContainer, RoundedCornerShape(8.dp))
                .height((configuration.screenHeightDp * 0.2).dp)
                .width((configuration.screenWidthDp * 0.4).dp)

            ){
                Image(
                    modifier = Modifier.fillMaxSize(),
                    contentScale = ContentScale.Crop,
                    painter = rememberAsyncImagePainter(com.snaptrash.snaptrash.R.drawable.rifiuti_prova),
                    contentDescription = "My Image"
                )

            }
            Row(){
                Text("Location")
            }


        }

    }

}

