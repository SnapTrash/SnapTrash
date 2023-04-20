package com.snaptrash.snaptrash.view.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.snaptrash.snaptrash.R


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AboutUsScreen() {
    val longText =

    Column(
        modifier = Modifier.padding(30.dp)
    ) {
        Text(text = stringResource(R.string.word_about_us), fontWeight = FontWeight.SemiBold, fontSize = 24.sp, color = MaterialTheme.colorScheme.secondary)
        Spacer(modifier = Modifier.height(20.dp))
        Box(
            Modifier
                .height(600.dp)
                .verticalScroll(rememberScrollState())
        ) {
            Text(text = stringResource(id = R.string.text_about_us),fontSize = 16.sp,
                color = MaterialTheme.colorScheme.secondary,
                textAlign = TextAlign.Justify
            )
        }
    }
}