package com.snaptrash.snaptrash.view.screens

import android.annotation.SuppressLint
import android.content.Context
import android.net.Uri
import android.util.Log
import androidx.camera.core.CameraSelector
import androidx.camera.core.ImageCapture
import androidx.camera.core.ImageCaptureException
import androidx.camera.core.Preview
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.camera.view.PreviewView
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CreditCard
import androidx.compose.material.icons.filled.Done
import androidx.compose.material.icons.filled.FlipCameraAndroid
import androidx.compose.material.icons.filled.FlipCameraIos
import androidx.compose.material.icons.outlined.Done
import androidx.compose.material.icons.outlined.FlipCameraIos
import androidx.compose.material.icons.outlined.RestartAlt
import androidx.compose.material.icons.sharp.Lens
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.core.content.ContextCompat
import androidx.core.net.toFile
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import coil.compose.*
import coil.request.ImageRequest
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.snaptrash.snaptrash.BuildConfig
import com.snaptrash.snaptrash.R
import com.snaptrash.snaptrash.model.data.Snap
import com.snaptrash.snaptrash.model.data.SnapStatus
import com.snaptrash.snaptrash.model.data.Urgency
import com.snaptrash.snaptrash.view.navigator.MainAddressBook
import com.snaptrash.snaptrash.viewmodel.CameraViewModel
import com.squareup.moshi.Moshi
import com.squareup.moshi.adapters.Rfc3339DateJsonAdapter
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import org.osmdroid.util.GeoPoint

import java.io.File
import java.net.URLEncoder
import java.nio.file.Files
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.util.*
import java.util.concurrent.Executor
import java.util.concurrent.Executors
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine
@Composable
fun CameraScreen(navController: NavController,location: GeoPoint,vm: CameraViewModel = viewModel()){
    val cameraExecutor = Executors.newSingleThreadExecutor()
    if(!vm.takeImage.value){
        /*AsyncImage(
            model = vm.photoUri.value,
            placeholder = painterResource(R.drawable.final_logo),
            contentDescription = null,
            modifier = Modifier.fillMaxSize()
        )*/
        ShowImageTaken(vm,location,navController)
    }
    else
        CameraView(cameraExecutor,
            {uri ->
                run {
                    vm.handleImageCapture(uri)
                }
            },
            { Log.e("kilo", "View error:", it) })
}


@Composable
fun ShowImageTaken(vm: CameraViewModel = viewModel(),location: GeoPoint,navController: NavController) {
    val configuration = LocalConfiguration.current
    Column(
        modifier = Modifier.padding(40.dp),
        horizontalAlignment = Alignment.CenterHorizontally


    ) {
        Box(
            Modifier
                .height((configuration.screenHeightDp * 0.6).dp)
                .width((configuration.screenWidthDp * 0.75).dp)
                .border(
                    width = 2.dp,
                    color = MaterialTheme.colorScheme.primary,
                )
                .background(color = MaterialTheme.colorScheme.surface)
        ) {
            AsyncImage(
                model = vm.photoUri.value,
                placeholder = painterResource(R.drawable.final_logo),
                contentDescription = null,
                modifier = Modifier.fillMaxSize(),
                contentScale = ContentScale.Crop,
            )

        }
        Spacer(modifier = Modifier.height(30.dp))
        Row{
            Button(
                onClick = {
                    vm.takeImage.value = true
                },
                modifier = Modifier
                    .width(150.dp)
                    .height(70.dp)

            ) { //button composable contains an other composable

                Icon(
                    Icons.Outlined.RestartAlt,
                    contentDescription = "Retake Icon",
                    modifier = Modifier.fillMaxSize(),
                )
                /*
                Text(
                    text = stringResource(R.string.word_retake),
                    fontSize = 20.sp,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.fillMaxWidth(),
                    color = MaterialTheme.colorScheme.onPrimaryContainer
                )

                 */
            }
            Spacer(modifier = Modifier.width(10.dp))
            Button(
                onClick = {
                          val snap = Snap("",
                              com.google.firebase.firestore.GeoPoint(location.latitude,location.longitude),
                              Firebase.auth.uid!!,
                              URLEncoder.encode(vm.photoUri.value.toString(),"UTF-8"),
                              "",
                              Date(),
                              "",
                              Urgency.NOT_URGENT,
                              Urgency.NOT_URGENT,
                              SnapStatus.PENDING)
                    val moshi = Moshi.Builder().add(Date::class.java, Rfc3339DateJsonAdapter().nullSafe()).addLast(
                        KotlinJsonAdapterFactory()
                    ).build()
                    val adapter = moshi.adapter(Snap::class.java)
                    val snapJson = adapter.toJson(snap)
                    navController.navigate(MainAddressBook.SINGLE_SNAP.replace("{snap}",snapJson))
                },
                modifier = Modifier
                    .width(150.dp)
                    .height(70.dp)

            ) { //button composable contains an other composable

                Icon(
                    Icons.Outlined.Done,
                    contentDescription = "Done Icon",
                    modifier = Modifier.fillMaxSize(),
                )
                /*
                Text(
                    //text = "Submit",
                    text = stringResource(R.string.word_done),
                    fontSize = 20.sp,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.fillMaxWidth(),
                    color = MaterialTheme.colorScheme.onPrimaryContainer
                )

                 */


            }


        }


    }
}


@Composable
fun CameraView(
    executor: Executor,
    onImageCaptured: (Uri) -> Unit,
    onError: (ImageCaptureException) -> Unit

){
    val lensFacing = CameraSelector.LENS_FACING_BACK
    val context = LocalContext.current
    val lifecycleOwner = LocalLifecycleOwner.current

    val preview = Preview.Builder().build()
    val previewView = remember { PreviewView(context)}
    val imageCapture: ImageCapture = remember {ImageCapture.Builder().build()}
    val cameraSelector = CameraSelector.Builder()
        .requireLensFacing(lensFacing)
        .build()
    // 2
    LaunchedEffect(lensFacing) {
        val cameraProvider = context.getCameraProvider()
        cameraProvider.unbindAll()
        cameraProvider.bindToLifecycle(
            lifecycleOwner,
            cameraSelector,
            preview,
            imageCapture
        )

        preview.setSurfaceProvider(previewView.surfaceProvider)
    }

    //3
    Box(
        contentAlignment = Alignment.BottomCenter,
        modifier = Modifier
            .fillMaxSize()

    ){
        AndroidView({ previewView}, modifier = Modifier.fillMaxSize())

        IconButton(modifier = Modifier
            .padding(bottom = 20.dp)
            .size(80.dp)
            ,
            onClick = {
                takePhoto(
                    filenameFormat = "yyyy-MM-dd-HH-mm-ss-SSS",
                    imageCapture = imageCapture,
                    executor = executor,
                    onImageCaptured = onImageCaptured,
                    onError = onError
                )
            },
            content = {
                Icon(
                    imageVector = Icons.Sharp.Lens,
                    contentDescription = "Take picture",
                    tint = Color.White,
                    modifier = Modifier
                        .size(100.dp)
                        .padding(1.dp)
                        .border(1.dp, Color.White, CircleShape)


                )
            }

        )
    }


}



private fun takePhoto(
    filenameFormat: String,
    imageCapture: ImageCapture,
    executor: Executor,
    onImageCaptured: (Uri) -> Unit,
    onError: (ImageCaptureException) -> Unit


) {
    val photoFile = File.createTempFile(
        BuildConfig.APPLICATION_ID,
        SimpleDateFormat(filenameFormat, Locale.US).format(System.currentTimeMillis()) + ".jpg"
    )

    val outputOptions = ImageCapture.OutputFileOptions.Builder(photoFile).build()

    imageCapture.takePicture(outputOptions, executor, object : ImageCapture.OnImageSavedCallback {
        override fun onError(exception: ImageCaptureException) {
            Log.i("kilo", "Take photo error:", exception)
            onError(exception)
        }

        override fun onImageSaved(outputFileResults: ImageCapture.OutputFileResults) {
            val savedUri = Uri.fromFile(photoFile)
            onImageCaptured(savedUri)

        }
    })
}

private suspend fun Context.getCameraProvider(): ProcessCameraProvider = suspendCoroutine { continuation ->
    ProcessCameraProvider.getInstance(this).also { cameraProvider ->
        cameraProvider.addListener({
            continuation.resume(cameraProvider.get())
        }, ContextCompat.getMainExecutor(this))
    }
}