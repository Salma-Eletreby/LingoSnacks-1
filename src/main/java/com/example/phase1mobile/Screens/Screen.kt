package com.example.phase1mobile.Screens

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Face
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.outlined.AddCircle
import androidx.compose.material.icons.outlined.Face
import androidx.compose.ui.graphics.vector.ImageVector

sealed class Screen(val route: String, val title: String, val icon: ImageVector){
    //declare objects
    object LoginScreen : Screen(route ="login", title ="Login", icon = Icons.Default.CheckCircle)
    object CreateAccount : Screen(route ="create", title ="create", icon = Icons.Default.Add)
    object StudentScreen: Screen(route="student",title="student",icon = Icons.Outlined.Face)
    object PackagesList : Screen(route = "packagesList", title = "Packages", icon = Icons.Default.Home)
    object PackageDetails : Screen(route = "packageDetails", title = "Package Details", icon = Icons.Default.Info)
    object AddEditPackage: Screen(route = "package form", title="Package form", icon = Icons.Outlined.AddCircle)
    object viewWordDetails: Screen(route ="word", title="Word Details", icon= Icons.Default.Face)
}

