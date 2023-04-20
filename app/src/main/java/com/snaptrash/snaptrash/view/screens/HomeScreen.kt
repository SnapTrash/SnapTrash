package com.snaptrash.snaptrash.view.HomeScreen

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.snaptrash.snaptrash.R


@Composable
fun HomeScreen() {

    Column(
        modifier = Modifier.padding(40.dp),
    ) {
        Spacer(modifier = Modifier.height(50.dp))
        Box(
            Modifier
                .height(400.dp)
                .width(300.dp)
                .align(Alignment.CenterHorizontally)
                //.aspectRatio(1.0f) // fixed aspect ratio could be usefull to resize the map
                .border(
                    width = 2.dp,
                    color = MaterialTheme.colorScheme.primary,

                    )
                .background(color = Color.White)
        ) {
            //put the initial map inside the box as in the screen in figma

        }
        Spacer(modifier = Modifier.height(30.dp))
        Button(
            onClick = {  //on click go to CameraView already implement
            },
            modifier = Modifier
                .fillMaxWidth()
                .height(70.dp)

        ) { //button composable contains an other composable
            Text(
                //text = "Submit",
                text = stringResource(R.string.instruction_take_a_snap),
                fontSize = 24.sp,
                textAlign = TextAlign.Center,
                modifier = Modifier.fillMaxWidth()
            )
        }

    }


}