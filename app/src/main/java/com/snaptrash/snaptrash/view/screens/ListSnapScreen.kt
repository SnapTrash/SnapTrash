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
import androidx.navigation.compose.rememberNavController
import com.snaptrash.snaptrash.view.commonwidgets.SnapCard


@Composable
fun ListSnapScreen(){
    LazyColumn(modifier = Modifier.fillMaxSize()){
        item{
            SnapCard()
        }
        item{
            SnapCard()
        }
        item{
            SnapCard()
        }
        item{
            SnapCard()
        }
        item{
            SnapCard()
        }
        item{
            SnapCard()
        }
        item{
            SnapCard()
        }
    }
}