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
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Share
import androidx.compose.material.icons.filled.Undo
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.font.FontWeight
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
import com.snaptrash.snaptrash.model.data.Urgency
import com.snaptrash.snaptrash.view.commonwidgets.ErrorCard
import com.snaptrash.snaptrash.viewmodel.MainNavViewModel
import com.snaptrash.snaptrash.viewmodel.SnapScreenViewModel
import java.net.URLDecoder


@Composable
fun OpenSnapScreen(snap: Snap,navController: NavController,mainNavViewModel: MainNavViewModel,isNew: Boolean,vm: SnapScreenViewModel = viewModel()) {
    val primaryColorTrasparent = MaterialTheme.colorScheme.primary.copy(alpha = 0.6f)
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
    mainNavViewModel.currentFloatingActionButton.value = {
        FloatingActionButton(
            onClick = {
                if(!vm.inProgress.value) {
                    if (!isNew) showDeleteDialog.value = true
                    else {
                        showDeleteDialog.value = false
                        vm.doSnapFunction()
                    }
                }
            }
        ) {
            if(vm.inProgress.value) CircularProgressIndicator() else {
                Icon(
                    if (!isNew) Icons.Filled.Delete else Icons.Filled.Add,
                    contentDescription = null,
                )
                if (showDeleteDialog.value && !isNew) {
                    DeleteDialog(onDismiss = { showDeleteDialog.value = false }, {
                        vm.doSnapFunction()
                    })
                }
            }
        }
    }
    val configuration = LocalConfiguration.current
    LaunchedEffect(snap) {
        if (isNew)
            vm.snapUrl.value = Uri.parse(snap.snapImageUrl)
        else
            vm.snapUrl.value = SnapImageDownloader.downloadSnap(context,snap)
    }
    Column {
        Box(
            Modifier
                .fillMaxWidth()
                .requiredHeight((configuration.screenHeightDp * 0.25).dp)

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
            modifier = Modifier
                .padding(20.dp)
                .fillMaxHeight()
                .fillMaxWidth()
                .verticalScroll(rememberScrollState())
        ) {
            if(vm.error.value != null){
                ErrorCard(error = stringResource(id = vm.error.value!!))
                Spacer(modifier = Modifier.height(10.dp))
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
            Spacer(modifier = Modifier.height(5.dp))
            Box(
                Modifier
                    .height((configuration.screenHeightDp * 0.25).dp)
                    .fillMaxWidth()
                    //.aspectRatio(1.0f) // fixed aspect ratio
                    //.clip(RoundedCornerShape(8.dp))
                    .border(
                        width = 1.dp,
                        color = MaterialTheme.colorScheme.primaryContainer,
                        //shape = RoundedCornerShape(8.dp)
                    )
                    .background(color = MaterialTheme.colorScheme.surfaceVariant)
            ) {
                OutlinedTextField(
                    value = vm.description.value,
                    enabled = isNew,
                    modifier = Modifier.fillMaxSize(),
                    onValueChange ={vm.description.value = it})
            }
            Spacer(Modifier.height(20.0.dp))
            Text(stringResource(com.snaptrash.snaptrash.R.string.word_rgency),fontSize = 18.sp, color = MaterialTheme.colorScheme.primary)
            Slider(
                value = vm.urgency.value.value.toFloat(),
                enabled = isNew ,
                steps = Urgency.values().size,
                valueRange = 0.0f..Urgency.values().size.toFloat() - 1.0f,
                onValueChange = {vm.urgency.value = Urgency.values()[it.toInt()]}
            )
            Text(
                text = when(vm.urgency.value){
                    Urgency.NOT_URGENT -> stringResource(com.snaptrash.snaptrash.R.string.not_urgent)
                    Urgency.SERIOUS -> stringResource(com.snaptrash.snaptrash.R.string.word_serious)
                    Urgency.URGENT -> stringResource(com.snaptrash.snaptrash.R.string.word_urgent)
                },
                modifier = Modifier.fillMaxWidth(),
                color = MaterialTheme.colorScheme.primary,
                fontSize = 16.sp
            )
            Spacer(modifier = Modifier.height(25.dp))
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




