package com.snaptrash.snaptrash.view.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.ClickableText
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.navOptions
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.snaptrash.snaptrash.R
import com.snaptrash.snaptrash.view.navigator.AuthAddressBook
import com.snaptrash.snaptrash.view.commonwidgets.TopBarLogin


@Composable
fun LoginScreen(navController: NavController){
    Column {
        TopBarLogin()
        LoginBody(navController)

    }
}@Composable
fun LoginBody(navController: NavController){
    var username: String by remember { mutableStateOf("") }
    var password: String by remember { mutableStateOf("") }
    var passwordVisible by rememberSaveable { mutableStateOf(false) }
    val primaryColorTrasparent = MaterialTheme.colorScheme.primary.copy(alpha = 0.5f)
    Column(
        modifier = Modifier
            .padding(8.dp)
            .fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        //verticalArrangement = Arrangement.Center

    ){
        Text(
            text = "",
            fontSize = 100.sp,
        )
        OutlinedTextField(
            value = username,
            onValueChange = {username = it},
            trailingIcon = { Icon(imageVector = Icons.Default.Email, contentDescription = stringResource(
                            R.string.content_desc_email_icon)
                        ) },
            label = {Text(text= stringResource(R.string.word_username))},
            placeholder = { Text(text = stringResource(R.string.instruction_type_username)) },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
            singleLine = true,
            modifier = Modifier.fillMaxWidth(),
            colors = OutlinedTextFieldDefaults.colors(
                focusedContainerColor = Color.White,
                unfocusedContainerColor = Color.White,
                disabledContainerColor = Color.White,
                focusedBorderColor = MaterialTheme.colorScheme.primary,
                unfocusedBorderColor = MaterialTheme.colorScheme.primary,
                focusedLabelColor = MaterialTheme.colorScheme.primary,
                unfocusedLabelColor = MaterialTheme.colorScheme.primary,
            ),
        )
        Text(
            text = "",
            fontSize = 6.sp,
        )
        OutlinedTextField(
            value = password,
            visualTransformation = if (passwordVisible) VisualTransformation.None else PasswordVisualTransformation(),
            onValueChange = {password = it},
            trailingIcon = { Icon(imageVector = Icons.Default.Lock, contentDescription = stringResource(
                            R.string.content_desc_lock_icon)
                        ) },
            label = {Text(text= stringResource(R.string.word_password))},
            placeholder = { Text(text = stringResource(R.string.instruction_type_password)) },
            singleLine = true,
            modifier = Modifier.fillMaxWidth(),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
            colors = OutlinedTextFieldDefaults.colors(
                focusedContainerColor = Color.White,
                unfocusedContainerColor = Color.White,
                disabledContainerColor = Color.White,
                focusedBorderColor = MaterialTheme.colorScheme.primary,
                unfocusedBorderColor = MaterialTheme.colorScheme.primary,
                focusedLabelColor = MaterialTheme.colorScheme.primary,
                unfocusedLabelColor = MaterialTheme.colorScheme.primary,
            ),
        )
        Text(
            text = "",
            fontSize = 12.sp,
        )
        Button(
            onClick = {
                      Firebase.auth.signInWithEmailAndPassword(username,password)
            },
            //modifier = androidx.compose.ui.Modifier.fillMaxWidth(),
            modifier = Modifier
                .width(235.dp)
                .height(95.dp)
                .padding(top = 36.dp),
            //colors =  ButtonDefaults.buttonColors.

        ){ //button composable contains an other composable
            Text(
                //text = "Submit",
                text = stringResource(R.string.word_login),
                fontSize = 24.sp,
                textAlign = TextAlign.Center,
                modifier = Modifier.fillMaxWidth()
            )
        }
        Text(
            text = "",
            fontSize = 20.sp,
        )
        SignUpClickableText(navController)
        Text(
            text = "",
            fontSize = 100.sp,
        )
        Text(
            text = stringResource(R.string.text_by_snaptrash),
            color = MaterialTheme.colorScheme.primary,
            fontSize = 16.sp,
            textAlign = TextAlign.Center,
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 64.dp)
        )
    }

}

@Composable
fun SignUpClickableText(navController: NavController) {
    val annotatedText = buildAnnotatedString {
        //append your initial text
        withStyle(
            style = SpanStyle(
                color = Color.Gray,
            )
        ) {
            append(stringResource(R.string.text_no_account_yet_sign_up))

        }

        //Start of the pushing annotation which you want to color and make them clickable later
        pushStringAnnotation(
            tag = "here",// provide tag which will then be provided when you click the text
            annotation = " " + stringResource(R.string.word_here)
        )
        //add text with your different color/style
        withStyle(style = SpanStyle(color = MaterialTheme.colorScheme.primary)
        ) {
            append(" " + stringResource(R.string.word_here))
        }
        withStyle(
            style = SpanStyle(
                color = Color.Gray,
            )
        ) {
            append("!")

        }
        // when pop is called it means the end of annotation with current tag
        pop()
    }

    ClickableText(
        text = annotatedText,
        onClick = { offset ->
            annotatedText.getStringAnnotations(
                tag = "here",// tag which you used in the buildAnnotatedString
                start = offset,
                end = offset
            ).takeIf { it.isNotEmpty() }?.get(0).let { annotation ->
                if (annotation != null) {
                    if (annotation.tag == "here") {
                        navController.navigate(AuthAddressBook.SIGN_UP, navOptions {
                            this.launchSingleTop = true
                            this.restoreState = true
                        })
                    }
                }
            }
        }

    )
}

