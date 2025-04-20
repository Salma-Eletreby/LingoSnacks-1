package com.example.phase1mobile.components

import android.content.res.Configuration
import android.widget.Toast
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.toRect
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.translate
import androidx.compose.ui.input.pointer.PointerIcon.Companion.Text
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.phase1mobile.R
import com.example.phase1mobile.Screens.Screen
import com.example.phase1mobile.nav.MyNavHost
import com.example.phase1mobile.repository.UserRepo
import com.example.phase1mobile.ui.theme.Phase1MobileTheme

@Composable
fun LoginVector(modifier: Modifier = Modifier){
    Box(){
        Canvas(
            modifier = Modifier
                .size(size = 500.dp)
                .padding(5.dp)
        ) {
            drawArc(
                color = Color(80, 200, 120),
                startAngle = 0f,
                sweepAngle = -180f,
                useCenter = true
            )
        }
        Image(
            painterResource(R.drawable.loginscreen),
            contentDescription = null,
            modifier = Modifier.fillMaxSize(),
            alignment = Alignment.TopCenter
        )
    }
}

@Composable
fun LoginVectorLandScape(modifier: Modifier = Modifier){
    Box(
        contentAlignment = Alignment.CenterStart
    ){
        Canvas(modifier) {
            drawCircle(Color(80, 200, 120), radius = 375.dp.toPx())
        }
        Image(
            painterResource(R.drawable.loginscreen),
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = modifier
                .align(Alignment.CenterStart)
                .fillMaxHeight(),
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LoginCard(navController: NavController,modifier: Modifier = Modifier){
    var email by rememberSaveable { mutableStateOf("") }
    var password by rememberSaveable { mutableStateOf("") }
    var isMissing by rememberSaveable { mutableStateOf(true) }

    var context = LocalContext.current

    Card(
        modifier = modifier
            .padding(20.dp)
            .height(500.dp),
        elevation = CardDefaults.cardElevation(
            defaultElevation =  10.dp,
        ),
        colors = CardDefaults.cardColors(
            containerColor = Color.White
        )
    ){
        Column(
            verticalArrangement = Arrangement.SpaceEvenly,
            modifier = modifier.fillMaxSize()
        ){
            Text(
                text = "Welcome To LingoSnacks",
                textAlign = TextAlign.Center,
                fontWeight = FontWeight.Bold,
                fontSize = 25.sp,
                modifier = modifier
                    .padding(10.dp)
                    .fillMaxWidth()
            )
            OutlinedTextField(
                value = email ,
                onValueChange = {
                    email = it
                    isMissing = email.isBlank() || password.isBlank()
                },
                label= { Text("Email")},
                modifier = modifier
                    .fillMaxWidth()
                    .padding(10.dp)
            )
            OutlinedTextField(
                value = password ,
                onValueChange = {
                    password = it
                    isMissing = email.isBlank() || password.isBlank()
                },
                label= { Text("Password")},
                modifier = modifier
                    .fillMaxWidth()
                    .padding(10.dp),
                visualTransformation = PasswordVisualTransformation(),
            )
            Button(
                onClick = {
                    if(isMissing == true){
                        Toast.makeText(context, "Required Field(s) is Missing", Toast.LENGTH_SHORT).show()
                    } else {
                        var result = UserRepo.checkUser(email, password)
                        if (result.contains("Incorrect")) {
                            password = ""
                            Toast.makeText(context, result, Toast.LENGTH_SHORT).show()
                        } else {
                            if (result.equals("Teacher")) {
                                navController.navigate("${Screen.PackagesList.route}/${email}") {
                                    launchSingleTop = true
                                    popUpTo(Screen.LoginScreen.route) {inclusive = true}
                                }
                            } else {
                                navController.navigate(Screen.StudentScreen.route) {
                                    launchSingleTop = true
                                    popUpTo(Screen.LoginScreen.route) {inclusive = true}
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
            ){
                Text("Login")
            }
            TextButton(onClick = {
                navController.navigate(Screen.CreateAccount.route){
                    launchSingleTop = true
                }
            },
                modifier = modifier
                    .fillMaxWidth()) {
                Text(text="New here? Create an Account",
                    fontWeight = FontWeight.ExtraBold,
                    )
            }
        }
    }
}

@Preview(showSystemUi = true)
@Composable
fun LoginVectorPreview(){
    Phase1MobileTheme {
        LoginVector()
    }
}

@Preview(showSystemUi = true, device = Devices.AUTOMOTIVE_1024p, widthDp = 640)
@Composable
fun LoginVectorLandscapePreview(){
    Phase1MobileTheme {
        LoginVectorLandScape()
    }
}
@Preview
@Composable
fun LoginCardPreview(){
    Phase1MobileTheme {
//        LoginCard()
    }
}