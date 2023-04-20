package com.snaptrash.snaptrash.view

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.navigation.compose.rememberNavController
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.functions.ktx.functions
import com.google.firebase.ktx.Firebase
import com.snaptrash.snaptrash.view.navigator.RootNav
import com.snaptrash.snaptrash.view.theme.SnapTrashTheme
import com.snaptrash.snaptrash.viewmodel.RootNavViewModel

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val rootNavViewModel = RootNavViewModel()
        rootNavViewModel.isLoggedIn.value = Firebase.auth.uid != null
        Firebase.auth.addAuthStateListener {
            rootNavViewModel.isLoggedIn.value = Firebase.auth.uid != null
        }
        Firebase.functions.useEmulator("10.0.2.2",5001)
        Firebase.auth.useEmulator("10.0.2.2",9099)
        Firebase.firestore.useEmulator("10.0.2.2", 8080)
        setContent {
            SnapTrashTheme() {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    RootNav(rememberNavController(), rootNavViewModel)
                }
            }
        }
    }
}