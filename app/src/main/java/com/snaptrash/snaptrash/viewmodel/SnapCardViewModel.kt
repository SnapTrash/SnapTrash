package com.snaptrash.snaptrash.viewmodel

import android.net.Uri
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel

class SnapCardViewModel : ViewModel() {
    var snapUri = mutableStateOf(Uri.EMPTY)
}
