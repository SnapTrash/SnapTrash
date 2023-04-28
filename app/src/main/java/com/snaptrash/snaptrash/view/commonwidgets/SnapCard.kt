package com.snaptrash.snaptrash.view.commonwidgets

import android.net.Uri
import android.util.Log
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberAsyncImagePainter
import com.snaptrash.snaptrash.model.SnapImageDownloader
import com.snaptrash.snaptrash.model.data.Snap
import java.net.URLDecoder

@Composable
fun SnapCard(snap: Snap,onClick: () -> Unit){
    val context = LocalContext.current
    var snapUri = remember { mutableStateOf<Uri>(Uri.EMPTY) }
    LaunchedEffect(snap) {
        snapUri.value = SnapImageDownloader.downloadSnap(context,snap)
    }
    Card(
        elevation =  CardDefaults.cardElevation(defaultElevation = 4.dp),
        modifier = Modifier
            .fillMaxWidth()
            //.width(200.dp)
            // .height(100.dp)
            .padding(12.dp)
            .clickable(onClick = onClick) ,
        shape = RoundedCornerShape(16.dp),
        border = BorderStroke(1.dp, MaterialTheme.colorScheme.onPrimary)
    ) {
        Row {
            Box(
                Modifier
                    .height(140.dp)
                    .width(140.dp)
                    .padding(15.dp)
                    //.aspectRatio(1.0f) // fixed aspect ratio
                    .clip(RoundedCornerShape(8.dp))
                    .border(
                        width = 1.dp,
                        color = MaterialTheme.colorScheme.onPrimary,
                        shape = RoundedCornerShape(8.dp)
                    ),
                contentAlignment = Alignment.Center
            ){
                if(snapUri.value == Uri.EMPTY) CircularProgressIndicator()
                else
                    Image(
                        modifier = Modifier.fillMaxSize(),
                        contentScale = ContentScale.Crop,
                        painter = rememberAsyncImagePainter(URLDecoder.decode(snapUri.value.toString(),"UTF-8"), onError = { Log.e("SNAPTRASH_IMAGE",it.result.throwable.message ?: "")}),
                        contentDescription = "My Image"
                    )

            }
            Spacer(modifier = Modifier
                .width(20.dp)
                .height(10.dp))
            Column(){
                Spacer(modifier = Modifier.height(10.dp))
                Row(){
                    Icon(
                        Icons.Filled.LocationOn,
                        contentDescription = "Location",
                        tint = MaterialTheme.colorScheme.onPrimary,
                        modifier = Modifier.size(20.dp)
                    )
                    Text(text ="${snap.location.latitude}, ${snap.location.longitude}", fontSize = 16.sp ,color = MaterialTheme.colorScheme.onPrimary )
                }
                Spacer(modifier = Modifier.height(10.dp))
                Row(){
                    Spacer(modifier = Modifier.width(20.dp))
                    Text(text = snap.description, fontSize = 16.sp, color = MaterialTheme.colorScheme.onPrimary )
                }
            }

        }
    }

}