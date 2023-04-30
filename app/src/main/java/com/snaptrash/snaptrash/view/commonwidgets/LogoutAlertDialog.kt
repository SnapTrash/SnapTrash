package com.snaptrash.snaptrash.view.commonwidgets

import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.snaptrash.snaptrash.R


@Composable
fun LogoutDialog(onDismiss: () -> Unit, onApproval: ()->Unit) {
    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text(text = stringResource(id = R.string.word_logout)) },
        text = { Text(text = stringResource(R.string.question_are_you_sure_you_want_to_logout)) },
        confirmButton = {
            Button(
                onClick = { onApproval() },
                content = { Text(text = stringResource(R.string.word_yes)) }
            )
        },
        dismissButton = {
            Button(
                onClick = onDismiss,
                content = { Text(text = stringResource(R.string.word_cancel)) }
            )
        }
    )
}