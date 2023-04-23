package com.snaptrash.snaptrash.view.screens

import android.content.Intent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.layout.R
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Share
import androidx.compose.material.icons.filled.Undo
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp


@Composable
fun OpenSnapScreen() {
    val primaryColorTrasparent = MaterialTheme.colorScheme.primary.copy(alpha = 0.5f)
    val secondaryColorTrasparent = MaterialTheme.colorScheme.secondary.copy(alpha = 0.7f)

    var showDeleteDialog : MutableState<Boolean> = remember { mutableStateOf(false) }

    var showShareDialog by remember { mutableStateOf(false) }

    val sendIntent: Intent = Intent().apply {
        action = Intent.ACTION_SEND
        putExtra(Intent.EXTRA_TEXT, "This is my text to send.")
        type = "text/plain"
    }
    val shareIntent = Intent.createChooser(sendIntent, null)

    Column(
    ) {
        Box(
            Modifier
                .fillMaxWidth()


        ) {
            Image(
                modifier = Modifier.fillMaxWidth(),
                contentScale = ContentScale.Crop,
                painter = painterResource(com.snaptrash.snaptrash.R.drawable.rifiuti_prova),
                contentDescription = "My Image"
            )

        }
        Spacer(
            modifier = Modifier
                .height(30.dp)
                .width(20.dp)
        )
        Row(
            verticalAlignment = Alignment.CenterVertically

        ) {
            Spacer(
                modifier = Modifier
                    .width(20.dp)
            )
            Text(text = "Trash #12345", fontSize = 22.sp, color = MaterialTheme.colorScheme.primary)
            Spacer(modifier = Modifier.width(140.dp))
            Icon(
                Icons.Filled.Undo,
                contentDescription = "GoBack",
                tint = MaterialTheme.colorScheme.primary,
                modifier = Modifier
                    .size(22.dp)
                    .clickable(onClick = {}) //to implement the go back to the listScreen
            )
            Spacer(modifier = Modifier.width(10.dp))
            Icon(
                Icons.Filled.Share,
                contentDescription = "Share",
                tint = MaterialTheme.colorScheme.primary,
                modifier = Modifier
                    .size(22.dp)
                    .clickable(onClick = {
                        showShareDialog = true
                    }) //to implement the share app
            )

        }
        Spacer(modifier = Modifier.height(15.dp))
        Column(
            modifier = Modifier.padding(20.dp)
        ) {
            Text(text = "Location:", fontSize = 18.sp, color = MaterialTheme.colorScheme.primary)
            Spacer(modifier = Modifier.height(5.dp))
            Text(
                text = "latitude, longitude",
                fontSize = 16.sp,
                color = MaterialTheme.colorScheme.secondary
            )
            Spacer(modifier = Modifier.height(20.dp))
            Text(text = "Location:", fontSize = 18.sp, color = MaterialTheme.colorScheme.primary)
            Box(
                Modifier
                    .height(200.dp)
                    .width(540.dp)

                    //.aspectRatio(1.0f) // fixed aspect ratio
                    //.clip(RoundedCornerShape(8.dp))
                    .border(
                        width = 1.dp,
                        color = MaterialTheme.colorScheme.primary,
                        //shape = RoundedCornerShape(8.dp)
                    )
                    .background(color = Color.White)
            ) {
                Row(
                    modifier = Modifier
                        .padding(5.dp)
                        .align(Alignment.BottomEnd),
                    //.width(IntrinsicSize.Max),
                    verticalAlignment = Alignment.Bottom
                ){
                    Icon(
                        Icons.Filled.Edit,
                        contentDescription = "Edit",
                        tint = secondaryColorTrasparent,
                        modifier = Modifier
                            .size(22.dp)
                            .clickable(onClick = {}) //to implement the share app
                    )

                }

                Text(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(5.dp),
                    text = "There are a lot of plastic bags and glasses close to the lake. \n" +
                            "The easiest way to reach it is via the parking lot at the end of “Lake Road”. "
                )



            }

        }
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(bottom = 16.dp),
            verticalArrangement = Arrangement.Bottom
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(70.dp)
                    .background(color = primaryColorTrasparent)
                    .clickable { showDeleteDialog.value = true }
                //.align(aligmnet = Alignment.Horizontal.CenterHorizontally)
            ) {
                Text(text = stringResource(com.snaptrash.snaptrash.R.string.label_delete_item),
                    color = Color.Red,
                    fontSize = 20.sp,
                    modifier = Modifier
                        .align(alignment = Alignment.Center)


                )
                if (showDeleteDialog.value) {
                    DeleteDialog(onDismiss = {showDeleteDialog.value = false})
                }



                //to implement function that delate the item and the ppop up to be sure of that


            }
        }
    }
}



@Composable
fun DeleteDialog(onDismiss: () -> Unit) {
    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text(text = stringResource(com.snaptrash.snaptrash.R.string.label_delete_item)) },
        text = { Text(text = stringResource(com.snaptrash.snaptrash.R.string.question_are_you_sure_to_delete)) },
        confirmButton = {
            Button(
                onClick = { /* Implement your share logic here */ },
                content = { Text(text = stringResource(com.snaptrash.snaptrash.R.string.word_delete)) }
            )
        },
        dismissButton = {
            Button(
                onClick = onDismiss,
                content = { Text(text = stringResource(com.snaptrash.snaptrash.R.string.label_go_back)) }
            )
        }
    )
}




