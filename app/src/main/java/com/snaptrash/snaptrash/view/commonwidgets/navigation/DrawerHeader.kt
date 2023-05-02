package com.snaptrash.snaptrash.view.commonwidgets.navigation

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.modifier.modifierLocalConsumer
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberAsyncImagePainter
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.snaptrash.snaptrash.R

@Composable
fun DrawerHeader(displayName: String){
    val configuration = LocalConfiguration.current
    Column(
        modifier = Modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height((configuration.screenHeightDp * 0.22).dp)
                .background(color = MaterialTheme.colorScheme.primaryContainer)
                .align(Alignment.CenterHorizontally),
            contentAlignment = Alignment.Center
        ) {
            Row(
                horizontalArrangement = Arrangement.SpaceEvenly ,
                modifier = Modifier
                    //.padding(5.dp)
                    .fillMaxWidth()
                    .align(Alignment.Center)

            ){
                Box(
                    modifier = Modifier
                        //.height(120.dp)
                        .height((configuration.screenHeightDp * 0.15).dp)
                        //.border(2.dp, Color.Red)
                        .align(Alignment.CenterVertically),

                ){
                    Image(
                        modifier = Modifier
                            .fillMaxHeight()
                            .clip(shape = CircleShape),
                            //.align(Alignment.Center),
                        contentScale = ContentScale.Fit,
                        painter =
                        if(Firebase.auth.currentUser?.photoUrl != null)
                            rememberAsyncImagePainter(Firebase.auth.currentUser?.photoUrl)
                        else
                            painterResource(R.drawable.profile_image),
                        contentDescription = "Profile Image"
                    )

                }
                Divider(
                    color = MaterialTheme.colorScheme.onPrimaryContainer,
                    thickness = 2.dp,
                    modifier = Modifier.width(1.dp)
                        .fillMaxHeight()
                )
                Box(
                    modifier = Modifier
                        //.height(100.dp)
                        .height((configuration.screenHeightDp * 0.15).dp)
                        .width(180.dp)
                        //.border(2.dp, Color.Red)
                        .align(Alignment.CenterVertically)

                ){
                    Column(){
                        Spacer(modifier = Modifier.height(5.dp))
                        Text(text =
                        displayName,
                            color = MaterialTheme.colorScheme.onPrimaryContainer,
                            fontWeight = FontWeight.Bold, fontSize = 24.sp
                        )
                        Spacer(modifier = Modifier.height(10.dp))
                        Text(text = "Points: 2594",
                            color = MaterialTheme.colorScheme.onPrimaryContainer,
                            fontSize = 20.sp,fontWeight = FontWeight.SemiBold)
                    }

                }

            }

            /*Text(
                stringResource(R.string.app_title),
                maxLines = 1,
                style = MaterialTheme.typography.titleLarge,
                color = MaterialTheme.colorScheme.background,
                overflow = TextOverflow.Ellipsis,
                modifier = Modifier
                    .align(Alignment.Center)
            )

             */
        }
    }
}
