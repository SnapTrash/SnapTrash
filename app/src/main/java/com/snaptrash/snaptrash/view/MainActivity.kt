package com.snaptrash.snaptrash.view

import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.location.LocationManager
import android.os.Build
import android.os.Bundle
import android.provider.Settings
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.navigation.compose.rememberNavController
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuth.AuthStateListener
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.firestore.ktx.firestoreSettings
import com.google.firebase.functions.ktx.functions
import com.google.firebase.ktx.Firebase
import com.snaptrash.snaptrash.BuildConfig
import com.snaptrash.snaptrash.model.DeviceInfo
import com.snaptrash.snaptrash.view.navigator.RootNav
import com.snaptrash.snaptrash.view.theme.SnapTrashTheme
import com.snaptrash.snaptrash.viewmodel.RootNavViewModel
import org.osmdroid.config.Configuration
import java.lang.Exception


class MainActivity : AppCompatActivity() {

    private val missingPermissions = mutableListOf<String>()
    private var wasLoggedIn = false
    private val firebaseAuthListener: AuthStateListener = AuthStateListener {
        if(wasLoggedIn && it.currentUser == null){
            recreate()
        }
        else if(it.currentUser != null){
            wasLoggedIn = true
        }
    }
    fun requestMissingPermissions(){
        val locationManager = getSystemService(Context.LOCATION_SERVICE) as LocationManager
        if(missingPermissions.size > 0) ActivityCompat.requestPermissions(this,missingPermissions.toTypedArray(),2)
        if(ContextCompat.checkSelfPermission(this,android.Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED){
            val providerEnabled = if(Build.VERSION.SDK_INT > 30)
                locationManager.isProviderEnabled(LocationManager.FUSED_PROVIDER)
            else
                locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)
            if(!providerEnabled){
                val intent = Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS)
                startActivity(intent)
            }
        }
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //Create navigation ViewModel
        val rootNavViewModel = RootNavViewModel()
        rootNavViewModel.isLoggedIn.value = Firebase.auth.uid != null
        //Init firebase
        Firebase.auth.addIdTokenListener( FirebaseAuth.IdTokenListener{
            rootNavViewModel.isLoggedIn.value = Firebase.auth.uid != null
            if(Firebase.auth.uid != null){
                rootNavViewModel.email.value = Firebase.auth.currentUser!!.email ?: ""
                rootNavViewModel.displayName.value = Firebase.auth.currentUser!!.displayName ?: ""
            }
            else{
                rootNavViewModel.email.value = ""
                rootNavViewModel.displayName.value = ""
            }

        })
        }
        Firebase.auth.addAuthStateListener(firebaseAuthListener)
        Firebase.firestore.firestoreSettings = firestoreSettings { isPersistenceEnabled = false }
        if(DeviceInfo.isEmulator){
            try {
                Firebase.auth.useEmulator("10.0.2.2", 9099)
                Firebase.firestore.useEmulator("10.0.2.2", 8080)
                Firebase.functions.useEmulator("10.0.2.2", 5001)
            }
            catch(_: Exception){

            }

        }
        //Init OSMDroid
        Configuration.getInstance().userAgentValue = BuildConfig.APPLICATION_ID
        setContent {
            SnapTrashTheme() {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    requestCameraPermission()
                    requestLocationPermission()
                    requestMissingPermissions()
                    RootNav(rememberNavController(), rootNavViewModel)
                    //AboutUsScreen()
                }
            }
        }
    }


    fun requestCameraPermission(){
        if(ContextCompat.checkSelfPermission(this,android.Manifest.permission.CAMERA)!= PackageManager.PERMISSION_GRANTED){
           missingPermissions.add(android.Manifest.permission.CAMERA)
        }
    }
    fun requestLocationPermission(){
       if(ContextCompat.checkSelfPermission(this,android.Manifest.permission.ACCESS_FINE_LOCATION)!= PackageManager.PERMISSION_GRANTED){
           missingPermissions.add(android.Manifest.permission.ACCESS_FINE_LOCATION)
       }
    }

    override fun onRestart() {
        super.onRestart()
        recreate()
    }
}