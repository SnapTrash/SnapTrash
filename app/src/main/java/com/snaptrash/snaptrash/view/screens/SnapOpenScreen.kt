package com.snaptrash.snaptrash.view.screens

import android.content.Intent
import android.net.Uri
import android.util.Log
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import coil.compose.rememberAsyncImagePainter
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.ktx.storage
import com.snaptrash.snaptrash.model.SnapImageDownloader
import com.snaptrash.snaptrash.model.data.Snap
import com.snaptrash.snaptrash.model.data.SnapStatus
import com.snaptrash.snaptrash.view.commonwidgets.ErrorCard
import com.snaptrash.snaptrash.viewmodel.SnapScreenViewModel
import java.net.URLDecoder


@Composable
fun OpenSnapScreen(snap: Snap,navController: NavController,isNew: Boolean,vm: SnapScreenViewModel = viewModel()) {
    val primaryColorTrasparent = MaterialTheme.colorScheme.primary.copy(alpha = 0.5f)
    val secondaryColorTrasparent = MaterialTheme.colorScheme.secondary.copy(alpha = 0.7f)
    var showDeleteDialog : MutableState<Boolean> = remember { mutableStateOf(false) }
    var showShareDialog by remember { mutableStateOf(false) }
    val context = LocalContext.current
    vm.isNew = isNew
    vm.snapId = snap.id
    if(!isNew) vm.description.value = snap.description
    vm.location.value = snap.location
    if(!isNew) vm.urgency.value = snap.urgency
    vm.navController = navController
    LaunchedEffect(snap) {
        vm.snapUrl.value = SnapImageDownloader.downloadSnap(context,snap)
    }
    Column {
        Box(
            Modifier
                .fillMaxWidth()
                .requiredHeight(250.dp)

        ) {
            if(vm.snapUrl.value == Uri.EMPTY)
                Row(modifier=Modifier.fillMaxWidth(),horizontalArrangement = Arrangement.Center){
                    Spacer(modifier = Modifier.height(5.dp))
                    CircularProgressIndicator()
                }
            else
                Image(
                    modifier = Modifier.fillMaxWidth(),
                    contentScale = ContentScale.Crop,
                    painter = rememberAsyncImagePainter(URLDecoder.decode(vm.snapUrl.value.toString(),"UTF-8"),onError = { Log.e("SNAPTRASH_IMAGE",it.result.throwable.message ?: "")}),
                    contentDescription = "My Image"
                )
        }
        Column(
            modifier = Modifier.padding(20.dp)
        ) {
            if(vm.error.value != null){
                ErrorCard(error = stringResource(id = vm.error.value!!))
                Spacer(modifier = Modifier.height(5.dp))
            }
            Text(text = stringResource(com.snaptrash.snaptrash.R.string.word_location), fontSize = 18.sp, color = MaterialTheme.colorScheme.primary)
            Spacer(modifier = Modifier.height(5.dp))
            Text(
                text = "${snap.location.latitude}, ${snap.location.longitude}",
                fontSize = 16.sp,
                color = MaterialTheme.colorScheme.secondary
            )
            Spacer(modifier = Modifier.height(20.dp))
            Text(text = stringResource(com.snaptrash.snaptrash.R.string.word_description), fontSize = 18.sp, color = MaterialTheme.colorScheme.primary)
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
                OutlinedTextField(
                    value = vm.description.value,
                    enabled = isNew,
                    modifier = Modifier.fillMaxSize(),
                    onValueChange ={vm.description.value = it})
            }

        }
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(bottom = 16.dp),
            verticalArrangement = Arrangement.Bottom
        ) {
            if(vm.inProgress.value) Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Center
            ){
                CircularProgressIndicator()
            }
            else if(snap.status == SnapStatus.PENDING) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(70.dp)
                        .background(color = if (!isNew) primaryColorTrasparent else MaterialTheme.colorScheme.primary)
                        .clickable {
                            if (!isNew) showDeleteDialog.value = true
                            else
                                vm.doSnapFunction()
                        }
                    //.align(aligmnet = Alignment.Horizontal.CenterHorizontally)
                ) {
                    Text(
                        text = if(!isNew) stringResource(com.snaptrash.snaptrash.R.string.label_delete_item) else stringResource(com.snaptrash.snaptrash.R.string.send_snap),
                        color = if (!isNew) MaterialTheme.colorScheme.error else MaterialTheme.colorScheme.onPrimary,
                        fontSize = 20.sp,
                        modifier = Modifier
                            .align(alignment = Alignment.Center)
                    )
                    if (showDeleteDialog.value && !isNew) {
                        DeleteDialog(onDismiss = { showDeleteDialog.value = false }, {
                            vm.doSnapFunction()
                        })
                    }
                }
            }
        }
    }
}



@Composable
fun DeleteDialog(onDismiss: () -> Unit,onApproval: ()->Unit) {
    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text(text = stringResource(com.snaptrash.snaptrash.R.string.label_delete_item)) },
        text = { Text(text = stringResource(com.snaptrash.snaptrash.R.string.question_are_you_sure_to_delete)) },
        confirmButton = {
            Button(
                onClick = {onApproval() },
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




