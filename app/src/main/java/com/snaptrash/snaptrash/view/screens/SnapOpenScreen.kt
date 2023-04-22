package com.snaptrash.snaptrash.view.screens

import android.content.Intent
import android.net.Uri
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
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberAsyncImagePainter
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.ktx.storage
import com.snaptrash.snaptrash.model.data.Snap


@Composable
fun OpenSnapScreen(snap: Snap) {
    val primaryColorTrasparent = MaterialTheme.colorScheme.primary.copy(alpha = 0.5f)
    val secondaryColorTrasparent = MaterialTheme.colorScheme.secondary.copy(alpha = 0.7f)
    var showDeleteDialog : MutableState<Boolean> = remember { mutableStateOf(false) }
    var showShareDialog by remember { mutableStateOf(false) }
    var snapUri by remember{ mutableStateOf(Uri.EMPTY) }
    if(snap.snapImageUrl.isNotEmpty()) {
        Firebase.storage.getReference("/snapImages/${snap.snapImageUrl}").downloadUrl.addOnSuccessListener {
            snapUri = it
        }
    }
    Column(
    ) {
        Box(
            Modifier
                .fillMaxWidth()


        ) {
            if(snapUri == Uri.EMPTY) CircularProgressIndicator()
            else
                Image(
                    modifier = Modifier.fillMaxWidth(),
                    contentScale = ContentScale.Crop,
                    painter = rememberAsyncImagePainter(snapUri),
                    contentDescription = "My Image"
                )
        }
        Column(
            modifier = Modifier.padding(20.dp)
        ) {
            Text(text = "Location:", fontSize = 18.sp, color = MaterialTheme.colorScheme.primary)
            Spacer(modifier = Modifier.height(5.dp))
            Text(
                text = "${snap.location.latitude}, ${snap.location.longitude}",
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
                    text = snap.description
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
                Text(text = "Delete item",
                    color = Color.Red,
                    fontSize = 20.sp,
                    modifier = Modifier
                        .align(alignment = Alignment.Center)
                )
                if (showDeleteDialog.value) {
                    DeleteDialog(onDismiss = {showDeleteDialog.value = false})
                }
                //TODO: implement delete
            }
        }
    }
}



@Composable
fun DeleteDialog(onDismiss: () -> Unit) {
    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text(text = "Delete item") },
        text = { Text(text = "Are you sure you want to delete this?") },
        confirmButton = {
            Button(
                onClick = { /* Implement your share logic here */ },
                content = { Text(text = "Delete") }
            )
        },
        dismissButton = {
            Button(
                onClick = onDismiss,
                content = { Text(text = "Go back") }
            )
        }
    )
}




