package com.snaptrash.snaptrash.view.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.ClickableText
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.withStyle
import androidx.navigation.NavController
import androidx.navigation.navOptions
import java.time.LocalDate
import java.util.*
import com.maxkeppeker.sheets.core.models.base.rememberUseCaseState
import com.maxkeppeler.sheets.calendar.CalendarDialog
import com.maxkeppeler.sheets.calendar.models.CalendarConfig
import com.maxkeppeler.sheets.calendar.models.CalendarSelection
import com.snaptrash.snaptrash.R
import com.snaptrash.snaptrash.view.navigator.AuthAddressBook
import com.snaptrash.snaptrash.view.commonwidgets.TopBarLogin


@Composable
fun SignUpScreen(navController: NavController){
    Column {
        TopBarLogin()
        SignUpBody(navController)

    }
}
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SignUpBody(navController: NavController){

    var name: String by remember { mutableStateOf("") }
    var surname: String by remember { mutableStateOf("") }

    var mobileCountryCode: String by remember {  mutableStateOf("") }
    var phoneNumber: String by remember { mutableStateOf("") }
    var email: String by remember { mutableStateOf("") }
    var password: String by remember { mutableStateOf("") }

    var day: Int by remember { mutableStateOf(LocalDate.now().dayOfMonth) }
    var month: Int by remember { mutableStateOf(LocalDate.now().monthValue) }
    var year: Int by remember { mutableStateOf(LocalDate.now().year) }

    var selectedDate: LocalDate = LocalDate.of(year,month,day)
    var passwordVisible by rememberSaveable { mutableStateOf(false) }
    var calendarState = rememberUseCaseState()
    val primaryColorTrasparent = MaterialTheme.colorScheme.primary.copy(alpha = 0.5f)
    var context = LocalContext.current
    val datePicker = CalendarDialog(
        state = calendarState,
        config = CalendarConfig(yearSelection = true, monthSelection = true),
        selection = CalendarSelection.Date {
        date ->
        run {
            day = date.dayOfMonth
            month = date.monthValue + 1
            year = date.year
        }
    } )
    Column(
        modifier = Modifier
            .padding(8.dp)
            .fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally){
        OutlinedTextField(
            value = name,
            onValueChange = {name = it},
            trailingIcon = { Icon(imageVector = Icons.Default.Person, contentDescription = stringResource(
                            R.string.word_person)
                        ) },
            label = { Text(text= stringResource(R.string.word_name)) },
            placeholder = { Text(text = stringResource(R.string.instruction_type_name)) },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
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
            value = surname,
            onValueChange = {surname = it},
            trailingIcon = { Icon(imageVector = Icons.Default.Person, contentDescription = stringResource(id = R.string.word_person)) },
            label = { Text(text= stringResource(R.string.word_surname)) },
            placeholder = { Text(text = stringResource(R.string.instruction_type_surname)) },
            singleLine = true,
            modifier = Modifier.fillMaxWidth(),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
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
            value = selectedDate.toString(),
            onValueChange = {},
            trailingIcon = { Icon(imageVector = Icons.Filled.CalendarToday, contentDescription = stringResource(
                            R.string.content_desc_calendar_icon)
                        ) },
            label = { Text(text= stringResource(R.string.word_birthdate)) },
            placeholder = { Text(text = stringResource(R.string.instruction_type_birthdate)) },
            singleLine = true,
            enabled = false,
            modifier = Modifier
                .fillMaxWidth()
                .clickable(onClick = {
                    calendarState.show()
                }
                ),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
            colors = OutlinedTextFieldDefaults.colors(
                disabledTextColor = MaterialTheme.colorScheme.primary,
                focusedContainerColor = Color.White,
                unfocusedContainerColor = Color.White,
                disabledContainerColor = Color.White,
                focusedBorderColor = MaterialTheme.colorScheme.primary,
                unfocusedBorderColor = MaterialTheme.colorScheme.primary,
                disabledBorderColor = MaterialTheme.colorScheme.primary,
                disabledTrailingIconColor = MaterialTheme.colorScheme.onSurfaceVariant,
                focusedLabelColor = MaterialTheme.colorScheme.primary,
                unfocusedLabelColor = MaterialTheme.colorScheme.primary,
                disabledLabelColor = MaterialTheme.colorScheme.primary,
                disabledPlaceholderColor = MaterialTheme.colorScheme.primary,
            ),
        )

        Text(
            text = "",
            fontSize = 6.sp,
        )

        OutlinedTextField(
            value = phoneNumber,
            onValueChange = {phoneNumber = it},
            trailingIcon = { Icon(imageVector = Icons.Default.Call, contentDescription = stringResource(
                            R.string.word_phone)
                        ) },
            label = { Text(text= stringResource(R.string.word_phone_number)) },
            placeholder = { Text(text = stringResource(R.string.instruction_type_phone_number)) },
            singleLine = true,
            modifier = Modifier.fillMaxWidth(),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
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
            value = email,
            onValueChange = {email = it},
            trailingIcon = { Icon(imageVector = Icons.Default.Mail, contentDescription = stringResource(
                            R.string.word_email)
                        ) },
            label = { Text(text= stringResource(
                R.string.word_email)) },
            placeholder = { Text(text = stringResource(R.string.instruction_type_email)) },
            singleLine = true,
            modifier = Modifier.fillMaxWidth(),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
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
            trailingIcon = { Icon(imageVector = Icons.Default.Lock, contentDescription = stringResource(id = R.string.content_desc_lock_icon)) },
            label = { Text(text= stringResource(id = R.string.word_password)) },
            placeholder = { Text(text = stringResource(id = R.string.instruction_type_password)) },
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
            fontSize = 10.sp,
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
                text = stringResource(R.string.word_sign_up),
                fontSize = 24.sp,
                textAlign = TextAlign.Center,
                modifier = Modifier.fillMaxWidth()
            )
        }
        Text(
            text = "",
            fontSize =10.sp,
        )
        LoginClickableText(navController)

        Text(
            text = stringResource(id = R.string.text_by_snaptrash),
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
fun LoginClickableText(navController: NavController) {
    val annotatedText = buildAnnotatedString {
        //append your initial text
        withStyle(
            style = SpanStyle(
                color = Color.Gray,
            )
        ) {
            append(stringResource(R.string.instruction_already_have_an_account))

        }

        //Start of the pushing annotation which you want to color and make them clickable later
        pushStringAnnotation(
            tag = "here",// provide tag which will then be provided when you click the text
            annotation = stringResource(id = R.string.word_here)
        )
        //add text with your different color/style
        withStyle(style = SpanStyle(color = MaterialTheme.colorScheme.primary)
        ) {
            append(" " + stringResource(id = R.string.word_here))
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
                        navController.navigate(AuthAddressBook.LOGIN, navOptions {
                            this.launchSingleTop = true
                            this.restoreState = true
                        })
                    }
                }
            }
        }

    )
}

