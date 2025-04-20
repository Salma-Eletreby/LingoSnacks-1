package com.example.phase1mobile.Screens

import android.app.Activity
import androidx.compose.material3.windowsizeclass.ExperimentalMaterial3WindowSizeClassApi
import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.material3.windowsizeclass.calculateWindowSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavController
import com.example.phase1mobile.components.CreateAccount
import com.example.phase1mobile.repository.UserRepo

@OptIn(ExperimentalMaterial3WindowSizeClassApi::class)
@Composable
fun CreateAccountScreen(navController: NavController, modifier: Modifier = Modifier){
    val context = LocalContext.current as Activity
    val con = LocalContext.current
    val windowSize = calculateWindowSizeClass(context)
    val isPortrait = windowSize.widthSizeClass == WindowWidthSizeClass.Compact

    UserRepo.getUsers(LocalContext.current)

    CreateAccount(navController)
}