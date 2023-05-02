package com.snaptrash.snaptrash.view.screens

import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.snaptrash.snaptrash.R
import com.snaptrash.snaptrash.view.commonwidgets.LoginTextBoxColors
import com.snaptrash.snaptrash.view.navigator.MainAddressBook


@Composable
fun PersonalDetailsScreen(navController: NavController){
    val scrollState = rememberScrollState()
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .verticalScroll(scrollState),
        horizontalAlignment = Alignment.CenterHorizontally
    ){
        Text(text = stringResource(id = R.string.personal_details), color = MaterialTheme.colorScheme.primary,
            fontWeight = FontWeight.SemiBold, fontSize = 24.sp,
        modifier = Modifier.padding(top = 30.dp, bottom = 10.dp))
        Spacer(modifier = Modifier.height(30.dp))
        ListPersonalDetails(navController)

    }

}


@Composable
fun ListPersonalDetails(navController : NavController) {
    val configuration = LocalConfiguration.current
    Column(
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        ListItem(
            modifier = Modifier
                .width((configuration.screenWidthDp * 0.85).dp)
                .clip(RoundedCornerShape(16.dp))
                .align(Alignment.CenterHorizontally)
                //.background(MaterialTheme.colorScheme.onPrimaryContainer, RoundedCornerShape(30.dp)),
                .border(1.dp, MaterialTheme.colorScheme.primary, RoundedCornerShape(16.dp))
                .clickable(onClick = {
                    navController.navigate(MainAddressBook.CHANGE_USERNAME)

                }), //go to screen to set this information
            headlineContent = { Text(text = stringResource(id = R.string.word_username)) },
            supportingContent = {Text(text = Firebase.auth.currentUser?.displayName ?:"Noname")},
            trailingContent = {
                Icon(
                    Icons.Filled.ChevronRight,
                    contentDescription = "Arrow Icon",
                )
            }
        )
        Spacer(modifier = Modifier.height(10.dp))
        ListItem(
            modifier = Modifier
                .width((configuration.screenWidthDp * 0.85).dp)
                .clip(RoundedCornerShape(16.dp))
                .align(Alignment.CenterHorizontally)
                //.background(MaterialTheme.colorScheme.onPrimaryContainer, RoundedCornerShape(30.dp)),
                .border(1.dp, MaterialTheme.colorScheme.primary, RoundedCornerShape(16.dp))
                .clickable(onClick = {}), //go to screen to set this information
            headlineContent = { Text(text = stringResource(id = R.string.word_email)) },
            supportingContent = {Text(text = Firebase.auth.currentUser?.email ?: "@snpatrash.com" )},
            trailingContent = {
                Icon(
                    Icons.Filled.ChevronRight,
                    contentDescription = "Arrow Icon",
                )

            }
        )

        Spacer(modifier = Modifier.height(10.dp))
        ListItem(
            modifier = Modifier
                .width((configuration.screenWidthDp * 0.85).dp)
                .clip(RoundedCornerShape(16.dp))
                .align(Alignment.CenterHorizontally)
                //.background(MaterialTheme.colorScheme.onPrimaryContainer, RoundedCornerShape(30.dp)),
                .border(1.dp, MaterialTheme.colorScheme.primary, RoundedCornerShape(16.dp))
                .clickable(onClick = {}), //go to screen to set this information
            headlineContent = { Text(text = stringResource(id = R.string.word_birthdate)) },
            supportingContent = {Text(text = "11 - 04 - 2001")}, //random date of birth, we'll be no clickable for now

            trailingContent = {
                Icon(
                    Icons.Filled.ChevronRight,
                    contentDescription = "Arrow Icon",
                )

            }
        )

    }
}


@Composable
fun ChangeUserNameScreen(){
    val scrollState = rememberScrollState()

    var newUserName : String = ""
    var newUserSurname: String = ""

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .verticalScroll(scrollState)
            .padding(20.dp),
        horizontalAlignment = Alignment.CenterHorizontally

    ){
        Text(text = stringResource(R.string.change_full_name), color = MaterialTheme.colorScheme.primary,
            fontWeight = FontWeight.SemiBold, fontSize = 24.sp,
            modifier = Modifier.padding(top = 30.dp, bottom = 10.dp))

        Spacer(modifier = Modifier.height(30.dp))

        OutlinedTextField(
            modifier = Modifier.fillMaxWidth(),
            enabled = false,
            value = Firebase.auth.currentUser?.displayName ?: "Noname",
            onValueChange ={},
            label = {Text(text = stringResource(id = R.string.word_current_fullname))},
            colors = LoginTextBoxColors.loginTextBoxColors()  )

        Spacer(modifier = Modifier.height(10.dp))

        OutlinedTextField(
            value = newUserName,
            onValueChange = { newUserName = it },
            label = { Text(text = stringResource(R.string.word_new_name)) },
            placeholder = { Text(text = stringResource(R.string.instruction_type_name)) },
            singleLine = true,
            modifier = Modifier.fillMaxWidth(),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
            colors = LoginTextBoxColors.loginTextBoxColors()
        )

        Spacer(modifier = Modifier.height(10.dp))

        OutlinedTextField(
            value = newUserSurname,
            onValueChange = { newUserSurname = it },
            label = { Text(text = stringResource(R.string.word_new_surname)) },
            placeholder = { Text(text = stringResource(id = R.string.instruction_type_surname)) },
            singleLine = true,
            modifier = Modifier.fillMaxWidth(),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
            colors = LoginTextBoxColors.loginTextBoxColors()
        )


        Spacer(modifier = Modifier.height(20.dp))
        //if(vm.inProgress.value) CircularProgressIndicator() else
        //{
        Button(
            //enabled = vm.fieldsValid ,
            onClick = {
                //vm.register()
            },
            //modifier = androidx.compose.ui.Modifier.fillMaxWidth(),
            modifier = Modifier
                .width(235.dp)
                .height(95.dp)
                .padding(top = 36.dp),
            //colors =  ButtonDefaults.buttonColors.

        ){ //button composable contains an other composable
            Text(
                text = stringResource(id = R.string.confirm),
                fontSize = 24.sp,
                textAlign = TextAlign.Center,
                modifier = Modifier.fillMaxWidth()
            )
        }

    }

}





@Composable
fun ChangePasswordScreen(){
    val scrollState = rememberScrollState()

    var passwordVisible by rememberSaveable { mutableStateOf(false) }
    var currentPassword : String = ""
    var newPassword : String = ""
    var confirmNewPassword : String = ""

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .verticalScroll(scrollState)
            .padding(20.dp),
        horizontalAlignment = Alignment.CenterHorizontally

    ){
        Text(text = stringResource(id = R.string.password_settings), color = MaterialTheme.colorScheme.primary,
            fontWeight = FontWeight.SemiBold, fontSize = 24.sp,
            modifier = Modifier.padding(top = 30.dp, bottom = 10.dp))

        Spacer(modifier = Modifier.height(30.dp))

        OutlinedTextField(
            value = currentPassword,
            visualTransformation = if (passwordVisible) VisualTransformation.None else PasswordVisualTransformation(),
            onValueChange = { currentPassword = it },
            trailingIcon = {
                Icon(
                    imageVector = Icons.Default.Lock,
                    contentDescription = stringResource(id = R.string.content_desc_lock_icon)
                )
            },
            label = { Text(text = stringResource(R.string.word_current_password)) },
            placeholder = { Text(text = stringResource(R.string.instruction_current_password)) },
            singleLine = true,
            modifier = Modifier.fillMaxWidth(),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
            colors = LoginTextBoxColors.loginTextBoxColors()
        )

        Spacer(modifier = Modifier.height(10.dp))

        OutlinedTextField(
            value = newPassword,
            visualTransformation = if (passwordVisible) VisualTransformation.None else PasswordVisualTransformation(),
            onValueChange = { newPassword = it },
            trailingIcon = {
                Icon(
                    imageVector = Icons.Default.Lock,
                    contentDescription = stringResource(id = R.string.content_desc_lock_icon)
                )
            },
            label = { Text(text = stringResource(R.string.word_new_password)) },
            placeholder = { Text(text = stringResource(R.string.instruction_type_new_password) ) },
            singleLine = true,
            modifier = Modifier.fillMaxWidth(),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
            colors = LoginTextBoxColors.loginTextBoxColors()
        )

        Spacer(modifier = Modifier.height(10.dp))

        OutlinedTextField(
            value = confirmNewPassword,
            visualTransformation = if (passwordVisible) VisualTransformation.None else PasswordVisualTransformation(),
            onValueChange = { confirmNewPassword = it },
            trailingIcon = {
                Icon(
                    imageVector = Icons.Default.Lock,
                    contentDescription = stringResource(id = R.string.content_desc_lock_icon)
                )
            },
            label = { Text(text = stringResource(R.string.confirm_password)) },
            placeholder = { Text(text = stringResource(R.string.instruction_type_new_password)) },
            singleLine = true,
            modifier = Modifier.fillMaxWidth(),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
            colors = LoginTextBoxColors.loginTextBoxColors()
        )

        Spacer(modifier = Modifier.height(20.dp))
        //if(vm.inProgress.value) CircularProgressIndicator() else
        //{
            Button(
                //enabled = vm.fieldsValid ,
                onClick = {
                    //vm.register()
                },
                //modifier = androidx.compose.ui.Modifier.fillMaxWidth(),
                modifier = Modifier
                    .width(235.dp)
                    .height(95.dp)
                    .padding(top = 36.dp),
                //colors =  ButtonDefaults.buttonColors.

            ){ //button composable contains an other composable
                Text(
                    text = stringResource(R.string.confirm),
                    fontSize = 24.sp,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.fillMaxWidth()
                )
            }

    }


}