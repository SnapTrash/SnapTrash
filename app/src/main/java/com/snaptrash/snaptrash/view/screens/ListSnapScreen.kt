package com.snaptrash.snaptrash.view.screens

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.layout.R
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.snaptrash.snaptrash.model.data.Snap
import com.snaptrash.snaptrash.model.data.SnapStatus
import com.snaptrash.snaptrash.view.commonwidgets.SnapCard
import com.snaptrash.snaptrash.view.navigator.MainAddressBook
import com.squareup.moshi.Moshi
import com.squareup.moshi.adapters.Rfc3339DateJsonAdapter
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import java.util.Date


@Composable
fun ListSnapScreen(snapList: List<Snap>,navController: NavController, isHistory: Boolean){
    LazyColumn(modifier = Modifier.fillMaxSize()){
        snapList.filter {(isHistory && it.status != SnapStatus.PENDING) || (!isHistory && it.status == SnapStatus.PENDING) }.
        forEach {
            item {
                SnapCard(snap = it){
                    val moshi = Moshi.Builder().add(Date::class.java, Rfc3339DateJsonAdapter().nullSafe()).addLast(KotlinJsonAdapterFactory()).build()
                    val adapter = moshi.adapter(Snap::class.java)
                    val snapJson = adapter.toJson(it)
                    navController.navigate(MainAddressBook.SINGLE_SNAP.replace("{snap}",snapJson))
                }
            }
        }
    }
}