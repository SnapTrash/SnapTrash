package com.snaptrash.snaptrash.view.AboutScreen

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AboutUsScreen() {
    val longText =
        "Welcome to SnapTrash! We are a dedicated team of environmental enthusiasts who are passionate about creating a cleaner and healthier environment for everyone. Our mission is to tackle the global issue of trash and litter by empowering users like you to report and take action against it. \n" +
                "\n" +
                "With our mobile application, you have the power to make a difference. Simply take a picture of trash you find anywhere - whether it's in nature, cities, mountains, lakes, or public places - and upload it along with the coordinates. Our platform then connects with our partner organizations responsible for waste management and cleanup, ensuring that the trash is promptly and responsibly taken care of. \n" +
                "\n" +
                "At SnapTrash, we believe that every piece of trash has a story, and that by sharing these stories, we can raise awareness about the impact of littering and inspire positive change. By using our app, you are not only helping to clean up the environment, but also contributing to a global community of like-minded individuals who are committed to protecting our planet for future generations."
    Column(
        modifier = Modifier.padding(30.dp)
    ) {
        Text(text = "About us", fontWeight = FontWeight.SemiBold, fontSize = 24.sp, color = MaterialTheme.colorScheme.secondary)
        Spacer(modifier = Modifier.height(20.dp))
        Box(
            Modifier
                .height(600.dp)
                .verticalScroll(rememberScrollState())
        ) {
            Text(text = longText,fontSize = 16.sp,
                color = MaterialTheme.colorScheme.secondary,
                textAlign = TextAlign.Justify
            )
        }
    }
}