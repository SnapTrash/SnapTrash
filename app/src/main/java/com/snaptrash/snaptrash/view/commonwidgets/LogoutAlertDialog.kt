package com.snaptrash.snaptrash.view.commonwidgets

import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase


@Composable
fun LogoutDialog(onDismiss: () -> Unit) {
    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text(text = "Logout") },
        text = { Text(text = "Are you sure you want to logout?") },
        confirmButton = {
            Button(
                onClick = { Firebase.auth.signOut() },
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