package com.snaptrash.snaptrash.view.commonwidgets.navigation

import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material3.Icon
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp

@Composable
fun FlagIconWithArrow(@DrawableRes flagResId: Int) {
    var arrowDirection by remember { mutableStateOf(false) }
    Row {
        Box(
            Modifier
                .padding(8.dp)
                .size(48.dp)
                .clickable(onClick = {
                    arrowDirection = !arrowDirection
                    /* Navigate to the "Language" screen */
                }),
            contentAlignment = Alignment.Center
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Image(
                    painter = painterResource(id = flagResId),
                    contentDescription = "Flag according to the actual language",
                    Modifier
                        .size(32.dp)
                )
                if (arrowDirection) {
                    Icon(
                        Icons.Filled.KeyboardArrowUp,
                        contentDescription = "Arrow",
                        Modifier.size(24.dp)
                    )
                } else {
                    Icon(
                        Icons.Filled.KeyboardArrowDown,
                        contentDescription = "Arrow",
                        Modifier.size(24.dp)
                    )
                }
            }

        }
    }
}