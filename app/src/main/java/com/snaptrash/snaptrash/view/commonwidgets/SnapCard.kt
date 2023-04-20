package com.snaptrash.snaptrash.view.commonwidgets

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
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.compose.rememberNavController
import com.snaptrash.snaptrash.R

@Composable
fun SnapCard(){
    val navControllerList = rememberNavController()
    Card(
        elevation =  CardDefaults.cardElevation(defaultElevation = 4.dp),
        modifier = Modifier
            .fillMaxWidth()
            //.width(200.dp)
            // .height(100.dp)
            .padding(12.dp)
            .clickable(onClick = {/* screen with info of snap, the screen want the variable to now the content of the snap */}) ,
        shape = RoundedCornerShape(16.dp),
        border = BorderStroke(1.dp, MaterialTheme.colorScheme.primary),
        colors =  CardDefaults.cardColors(containerColor = Color.White,
        )
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
                        color = MaterialTheme.colorScheme.primary,
                        shape = RoundedCornerShape(8.dp)
                    )



            ){
                Image(
                    modifier = Modifier.fillMaxSize(),
                    contentScale = ContentScale.Crop,
                    painter = painterResource(R.drawable.rifiuti_prova),
                    contentDescription = "My Image"
                )

            }
            Spacer(modifier = Modifier.width(20.dp)
                .height(10.dp))
            Column(){
                Spacer(modifier = Modifier.height(10.dp))
                Text(text = "Trash #12345", fontSize = 22.sp, color = MaterialTheme.colorScheme.primary )
                Spacer(modifier = Modifier.height(10.dp))
                Row(){
                    Icon(
                        Icons.Filled.LocationOn,
                        contentDescription = "Location",
                        tint = MaterialTheme.colorScheme.primary,
                        modifier = Modifier.size(20.dp)
                    )
                    Text(text ="longitude, latitude", fontSize = 16.sp ,color = MaterialTheme.colorScheme.primary )
                }
                Spacer(modifier = Modifier.height(10.dp))
                Row(){
                    Spacer(modifier = Modifier.width(20.dp))
                    Text("Data", fontSize = 16.sp, color = MaterialTheme.colorScheme.primary )
                }
            }

        }
    }

}