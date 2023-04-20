package com.snaptrash.snaptrash.view.CameraView

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
import androidx.compose.material.icons.sharp.Lens
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.core.content.ContextCompat
import androidx.core.net.toFile
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.*
import coil.request.ImageRequest
import com.snaptrash.snaptrash.R

import java.io.File
import java.nio.file.Files
import java.text.SimpleDateFormat
import java.util.*
import java.util.concurrent.Executor
import java.util.concurrent.Executors
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

class CameraViewModel: ViewModel(){
    var photoUri = mutableStateOf(Uri.EMPTY)
    var takeImage = mutableStateOf(true)
    fun handleImageCapture(uri: Uri) {
        Log.i("kilo", "Image captured: $uri")
        photoUri.value = uri
        takeImage.value = false
    }
}

@Composable
fun CameraScreen(vm: CameraViewModel = viewModel()){
    val outputDirectory = LocalContext.current.externalMediaDirs.firstOrNull()?.let {
        File(it, LocalContext.current.resources.getString(R.string.app_name)).apply { mkdirs() }
    }
    val cameraExecutor = Executors.newSingleThreadExecutor()
    if (outputDirectory != null) {
        if(!vm.takeImage.value){
            /*AsyncImage(
                model = vm.photoUri.value,
                placeholder = painterResource(R.drawable.final_logo),
                contentDescription = null,
                modifier = Modifier.fillMaxSize()
            )*/
            showImageTaken(vm)
        }
        else
            CameraView(outputDirectory,cameraExecutor,
                {uri ->
                    run {
                        vm.handleImageCapture(uri)
                    }
                },
                { Log.e("kilo", "View error:", it) })
    }
}


@Composable
fun showImageTaken(vm: CameraViewModel = viewModel()) {
    Column(
        modifier = Modifier.padding(40.dp)

    ) {
        Spacer(modifier = Modifier.height(50.dp))
        Box(
            Modifier
                .height(500.dp)
                .width(400.dp)

                //.aspectRatio(1.0f) // fixed aspect ratio
                //.clip(RoundedCornerShape(8.dp))
                .border(
                    width = 2.dp,
                    color = MaterialTheme.colorScheme.primary,
                    //shape = RoundedCornerShape(8.dp)
                )
                .background(color = Color.White)
        ) {
            AsyncImage(
                model = vm.photoUri.value,
                placeholder = painterResource(R.drawable.final_logo),
                contentDescription = null,
                modifier = Modifier.fillMaxSize()
            )

        }
        Spacer(modifier = Modifier.height(30.dp))
        Row(


        ) {
            Button(
                onClick = {
                    vm.takeImage.value = true
                },
                modifier = Modifier
                    .width(150.dp)
                    .height(70.dp)

            ) { //button composable contains an other composable
                Text(
                    //text = "Submit",
                    text = "Retake",
                    fontSize = 20.sp,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.fillMaxWidth()
                )
            }
            Spacer(modifier = Modifier.width(10.dp))
            Button(
                onClick = {
                },
                modifier = Modifier
                    .width(150.dp)
                    .height(70.dp)

            ) { //button composable contains an other composable
                Text(
                    //text = "Submit",
                    text = "Done",
                    fontSize = 20.sp,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.fillMaxWidth()
                )


            }


        }


    }
}


@Composable
fun CameraView(
    outputDirectory: File,
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
                    outputDirectory = outputDirectory,
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
    outputDirectory: File,
    executor: Executor,
    onImageCaptured: (Uri) -> Unit,
    onError: (ImageCaptureException) -> Unit


) {
    val photoFile = File(
        outputDirectory,
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