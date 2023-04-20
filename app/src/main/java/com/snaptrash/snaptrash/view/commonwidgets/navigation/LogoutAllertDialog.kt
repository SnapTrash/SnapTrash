package com.snaptrash.snaptrash.view.commonwidgets.navigation

import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable


@Composable
fun LogoutDialog(onDismiss: () -> Unit) {
    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text(text = "Logout") },
        text = { Text(text = "Are you sure you want to logout?") },
        confirmButton = {
            Button(
                onClick = { /* Implement your share logic here */ },
                content = { Text(text = "Yes") }
            )
        },
        dismissButton = {
            Button(
                onClick = onDismiss,
                content = { Text(text = "Cancel") }
            )
        }
    )
}