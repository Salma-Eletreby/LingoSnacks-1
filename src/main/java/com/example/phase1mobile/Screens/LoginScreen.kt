package com.example.phase1mobile.Screens

import android.app.Activity
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material3.Text
import androidx.compose.material3.windowsizeclass.ExperimentalMaterial3WindowSizeClassApi
import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.material3.windowsizeclass.calculateWindowSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.phase1mobile.components.LoginCard
import com.example.phase1mobile.components.LoginVector
import com.example.phase1mobile.components.LoginVectorLandScape
import com.example.phase1mobile.nav.MyNavHost
import com.example.phase1mobile.repository.UserRepo
import com.example.phase1mobile.ui.theme.Phase1MobileTheme

@Composable
fun LoginScreenPortrait(navController: NavController, modifier: Modifier = Modifier) {
    Box() {
        LoginVector()
        LoginCard(navController, modifier = Modifier.align(Alignment.BottomCenter))
    }
}

@Composable
fun LoginScreenLandscape(navController: NavController, modifier: Modifier = Modifier) {
    Row()
    {
        LoginVectorLandScape()
        LoginCard(navController)
    }
}


@OptIn(ExperimentalMaterial3WindowSizeClassApi::class)
@Composable
fun LoginScreen(navController: NavController, modifier: Modifier = Modifier) {
    val context = LocalContext.current as Activity
    val con = LocalContext.current
    val windowSize = calculateWindowSizeClass(context)
    val isPortrait = windowSize.widthSizeClass == WindowWidthSizeClass.Compact

    UserRepo.getUsers(LocalContext.current)

    if (isPortrait)
        LoginScreenPortrait(navController)
    else
        LoginScreenLandscape(navController)
}


@Preview(showSystemUi = true)
@Composable
fun LoginScreenPPreview() {
    Phase1MobileTheme {
//        LoginScreenPortrait()
    }
}

@Preview(showSystemUi = true, device = Devices.AUTOMOTIVE_1024p, widthDp = 640)
@Composable
fun LoginScreenLPreview() {
    Phase1MobileTheme {
//        LoginScreenLandscape()
    }
}

@Preview
@Composable
fun LoginScreenPreview() {
    Phase1MobileTheme {
//        LoginScreen()
    }
}