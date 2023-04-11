package com.snaptrash.snaptrash.view.LoginScreen

import android.util.Log
import androidx.compose.foundation.background
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
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp


@Composable
fun LoginScreen(){
    Column {
        topBarLogin()
        LoginBody()

    }
}



@Composable
fun LoginBody(){
    var username: String by remember { mutableStateOf("") }
    var password: String by remember { mutableStateOf("") }
    var passwordVisible by rememberSaveable { mutableStateOf(false) }
    val primaryColorTrasparent = MaterialTheme.colorScheme.primary.copy(alpha = 0.5f)
    Column(
        modifier = androidx.compose.ui.Modifier
            .padding(8.dp)
            .fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        //verticalArrangement = Arrangement.Center

    ){
        /*Text(
            text = "Login",
            color = MaterialTheme.colorScheme.primary,
            fontSize = 36.sp,
            fontWeight = FontWeight.SemiBold,
            textAlign = TextAlign.Center,
            modifier = androidx.compose.ui.Modifier
                .fillMaxWidth()
                .padding(top = 64.dp)
        )*/
        Text(
            text = "",
            fontSize = 100.sp,
        )

        OutlinedTextField(
            value = username,
            onValueChange = {username = it},
            trailingIcon = { Icon(imageVector = Icons.Default.Email, contentDescription = "Email icon") },
            label = {Text(text= "Username")},
            placeholder = { Text(text = "Type your username") },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
            singleLine = true,
            modifier = Modifier.fillMaxWidth(),
            colors = TextFieldDefaults.outlinedTextFieldColors(
                containerColor = Color.White,
                focusedBorderColor = MaterialTheme.colorScheme.primary,
                unfocusedBorderColor = MaterialTheme.colorScheme.primary,
                focusedLabelColor = MaterialTheme.colorScheme.primary,
                unfocusedLabelColor = MaterialTheme.colorScheme.primary,
                placeholderColor = MaterialTheme.colorScheme.primary,
                textColor = MaterialTheme.colorScheme.primary,


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
            trailingIcon = { Icon(imageVector = Icons.Default.Lock, contentDescription = "Lock icon") },
            label = {Text(text= "Password")},
            placeholder = { Text(text = "Type your password") },
            singleLine = true,
            modifier = Modifier.fillMaxWidth(),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
            colors = TextFieldDefaults.outlinedTextFieldColors(
                containerColor = Color.White,
                focusedBorderColor = MaterialTheme.colorScheme.primary,
                unfocusedBorderColor = MaterialTheme.colorScheme.primary,
                focusedLabelColor = MaterialTheme.colorScheme.primary,
                unfocusedLabelColor = MaterialTheme.colorScheme.primary,
                placeholderColor = MaterialTheme.colorScheme.primary,
                textColor = MaterialTheme.colorScheme.primary,


                ),
        )
        Text(
            text = "",
            fontSize = 12.sp,
        )
        Button(
            onClick = { },
            //modifier = androidx.compose.ui.Modifier.fillMaxWidth(),
            modifier = Modifier
                .width(235.dp)
                .height(95.dp)
                .padding(top = 36.dp),
            //colors =  ButtonDefaults.buttonColors.

        ){ //button composable contains an other composable
            Text(
                //text = "Submit",
                text = "Login",
                fontSize = 24.sp,
                textAlign = TextAlign.Center,
                modifier = Modifier.fillMaxWidth()
            )
        }
        Text(
            text = "",
            fontSize = 20.sp,
        )

        /*Text(
            text = buildAnnotatedString {
                append("No account yet? Sign up ")
                //withStyle(style = SpanStyle(color = MaterialTheme.colorScheme.primary), {
                    //append("here")
                    pushStringAnnotation(
                        tag = "Here",
                        annotation = "here"
                    )
                withStyle(style = SpanStyle(color = MaterialTheme.colorScheme.primary), {
                    append("here")

                })
                append("!")
                pop()
            }


        )
        ClickableText(text = , onClick = )
         */
        SignUpClickableText()
        Text(
            text = "",
            fontSize = 100.sp,
        )
        Text(
            text = "by SnapTrash",
            color = MaterialTheme.colorScheme.primary,
            fontSize = 16.sp,
            textAlign = TextAlign.Center,
            modifier = androidx.compose.ui.Modifier
                .fillMaxWidth()
                .padding(top = 64.dp)
        )
    }

}




@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun topBarLogin() {
    Column(
        modifier = Modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(100.dp)
                .background(color = MaterialTheme.colorScheme.primary)
        ) {
            Text(
                "SnapTrash",
                maxLines = 1,
                style = MaterialTheme.typography.titleLarge,
                color = MaterialTheme.colorScheme.background,
                overflow = TextOverflow.Ellipsis,
                modifier = Modifier
                    .align(Alignment.Center)
            )
        }
        // altri composable qui...
    }
}



@Composable
fun SignUpClickableText() {
    val annotatedText = buildAnnotatedString {
        //append your initial text
        withStyle(
            style = SpanStyle(
                color = Color.Gray,
            )
        ) {
            append("No account yet? Sign up ")

        }

        //Start of the pushing annotation which you want to color and make them clickable later
        pushStringAnnotation(
            tag = "here",// provide tag which will then be provided when you click the text
            annotation = "here"
        )
        //add text with your different color/style
        withStyle(style = SpanStyle(color = MaterialTheme.colorScheme.primary)
        ) {
            append("here")
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
            )[0].let { annotation ->
                //do your stuff when it gets clicked
                Log.d("Clicked", annotation.item)
            }
        }
    )
}