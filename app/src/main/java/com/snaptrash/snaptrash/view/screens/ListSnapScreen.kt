package com.snaptrash.snaptrash.view.screens

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import com.snaptrash.snaptrash.model.data.Snap
import com.snaptrash.snaptrash.view.commonwidgets.SnapCard
import com.snaptrash.snaptrash.view.navigator.MainAddressBook
import com.squareup.moshi.Moshi
import com.squareup.moshi.adapters.Rfc3339DateJsonAdapter
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import java.net.URLEncoder
import java.util.Date


@Composable
fun ListSnapScreen(snapList: List<Snap>,navController: NavController){
    val state = rememberLazyListState()
    LazyColumn(modifier = Modifier.fillMaxSize()
        ,state = state){
        snapList.forEach {
            item {
                SnapCard(snap = it,onClick = {
                    val moshi = Moshi.Builder().add(Date::class.java, Rfc3339DateJsonAdapter().nullSafe()).addLast(KotlinJsonAdapterFactory()).build()
                    val adapter = moshi.adapter(Snap::class.java)
                    val urlEncodedSnap = it.copy(snapImageUrl = URLEncoder.encode(it.snapImageUrl,"UTF-8"))
                    val snapJson = adapter.toJson(urlEncodedSnap)
                    navController.navigate(MainAddressBook.SINGLE_SNAP.replace("{snap}",snapJson))
                })
            }
        }
    }
}