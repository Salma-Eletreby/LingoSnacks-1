package com.example.phase1mobile.components

import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.phase1mobile.Screens.Screen
import com.example.phase1mobile.model.User
import com.example.phase1mobile.repository.UserRepo
import com.example.phase1mobile.ui.theme.Phase1MobileTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CreateAccount(navController: NavController, modifier: Modifier = Modifier) {
    var firstName by rememberSaveable { mutableStateOf("") }
    var lastName by rememberSaveable { mutableStateOf("") }
    var email by rememberSaveable { mutableStateOf("") }
    var password by rememberSaveable { mutableStateOf("") }
    var profile by rememberSaveable { mutableStateOf("") }
    var role by rememberSaveable { mutableStateOf("Teacher") }

    var isMissing by rememberSaveable { mutableStateOf(true) }

    val roles = listOf("Teacher", "Student")
    var context = LocalContext.current

    Card(
        elevation = CardDefaults.cardElevation(
            defaultElevation = 10.dp,
        ),
        colors = CardDefaults.cardColors(
            containerColor = Color.White
        ),
        modifier = modifier
            .padding(10.dp)
            .fillMaxHeight()
    ) {
        Column(
            modifier = modifier
                .verticalScroll(rememberScrollState())
                .padding(10.dp),
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = "Join Us",
                textAlign = TextAlign.Center,
                fontSize = 30.sp,
                modifier = modifier.fillMaxWidth()
            )
            OutlinedTextField(
                value = firstName,
                onValueChange = {
                    firstName = it
                    isMissing = firstName.isBlank()
                            || lastName.isBlank()
                            || email.isBlank()
                            || password.isBlank()
                            || role.isBlank()
                },
                label = { Text("First Name") },
                modifier = modifier
                    .fillMaxWidth()
                    .padding(10.dp)
            )
            OutlinedTextField(
                value = lastName,
                onValueChange = {
                    lastName = it
                    isMissing = firstName.isBlank()
                            || lastName.isBlank()
                            || email.isBlank()
                            || password.isBlank()
                            || role.isBlank()
                },
                label = { Text("Last Name") },
                modifier = modifier
                    .fillMaxWidth()
                    .padding(10.dp)
            )
            OutlinedTextField(
                value = email,
                onValueChange = {
                    email = it
                    isMissing = firstName.isBlank()
                            || lastName.isBlank()
                            || email.isBlank()
                            || password.isBlank()
                            || role.isBlank()
                },
                label = { Text("Email") },
                modifier = modifier
                    .fillMaxWidth()
                    .padding(10.dp)
            )
            OutlinedTextField(
                value = password,
                onValueChange = {
                    password = it
                    isMissing = firstName.isBlank()
                            || lastName.isBlank()
                            || email.isBlank()
                            || password.isBlank()
                            || role.isBlank()
                },
                label = { Text("Password") },
                modifier = modifier
                    .fillMaxWidth()
                    .padding(10.dp),
                visualTransformation = PasswordVisualTransformation()
            )
            OutlinedTextField(
                value = profile,
                onValueChange = {
                    profile = it
                    isMissing = firstName.isBlank()
                            || lastName.isBlank()
                            || email.isBlank()
                            || password.isBlank()
                            || role.isBlank()
                },
                label = { Text("Profile Image URL") },
                modifier = modifier
                    .fillMaxWidth()
                    .padding(10.dp)
            )
            Text(
                text = "Role",
                modifier = modifier
                    .fillMaxWidth()
                    .padding(10.dp)
            )
            roles.forEach { r ->
                Row(
                    modifier = modifier
                        .fillMaxWidth()
                        .selectable(
                            selected = (r == role),
                            onClick = { role = r }
                        )
                ) {
                    RadioButton(
                        selected = (r == role),
                        onClick = { role = r }
                    )
                    Text(
                        text = r,
                        modifier = modifier.padding(top = 10.dp)
                    )
                }
            }
            Button(
                onClick = {
                    if (isMissing == true) {
                        Toast.makeText(context, "Required Field(s) is Missing", Toast.LENGTH_SHORT)
                            .show()
                    } else {
                        var result = UserRepo.addUser(
                            User(
                                firstName = firstName,
                                lastName = lastName,
                                email = email,
                                password = password,
                                photoUri = profile,
                                role = role
                            )
                        )
                        //redirect to packages
                        if (result.contains("repeat")) {
                            Toast.makeText(context, "Email is already used!", Toast.LENGTH_SHORT)
                                .show()
                        } else {
                            if (role.equals("Teacher")) {
                                navController.navigate("${Screen.PackagesList.route}/${email}") {
                                    launchSingleTop = true
                                    popUpTo(Screen.LoginScreen.route) { inclusive = true }
                                }
                            } else {
                                navController.navigate(Screen.StudentScreen.route) {
                                    launchSingleTop = true
                                    popUpTo(Screen.LoginScreen.route) { inclusive = true }
                                }
                            }
                        }
                    }
                },
                modifier = modifier
                    .fillMaxWidth()
                    .padding(10.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = MaterialTheme.colorScheme.primary
                )
            ) {
                Text("Create Account")
            }
            TextButton(
                onClick = {
                    navController.navigate(Screen.LoginScreen.route) {
                        launchSingleTop = true
                    }
                },
                modifier = modifier
                    .fillMaxWidth()
            ) {
                Text(
                    text = "Already have an account? Click here to login",
                    fontWeight = FontWeight.ExtraBold,
                )
            }
        }
    }
}

@Preview(showSystemUi = true)
@Composable
fun CreateAccountPreview() {
    val navController = rememberNavController()
    Phase1MobileTheme {
        CreateAccount(navController)
    }
}