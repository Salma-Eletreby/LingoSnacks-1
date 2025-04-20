package com.example.phase1mobile.nav

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.phase1mobile.Screens.CreateAccountScreen
import com.example.phase1mobile.Screens.LoginScreen
import com.example.phase1mobile.Screens.PackageDetailScreen
import com.example.phase1mobile.Screens.PackageScreen
import com.example.phase1mobile.Screens.Screen
import com.example.phase1mobile.Screens.StudentScreen
import com.example.phase1mobile.Screens.WordScreen
import com.example.phase1mobile.components.UpdatePackageCard

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun MyNavHost(
    navHostController: NavHostController,
//              paddingValues: PaddingValues
) {
    NavHost(
        navController = navHostController,
        startDestination = Screen.LoginScreen.route,
        modifier = Modifier.fillMaxSize()
    )
    {
        composable(route = Screen.LoginScreen.route) {
            LoginScreen(navHostController)
        }

        composable(route = Screen.CreateAccount.route) {
            CreateAccountScreen(navHostController)
        }

        composable(route = Screen.StudentScreen.route) {
            StudentScreen()
        }

        composable(route = "${Screen.PackagesList.route}/{email}") { backStackEntry ->
            backStackEntry.arguments?.getString("email")
                ?.let { PackageScreen(user = it, navController = navHostController) }
        }

        composable(
            route = "${Screen.PackageDetails.route}/{email}/{pkgId}",
            arguments = listOf(navArgument("pkgId") { type = NavType.IntType })
        ) { backStackEntry ->
            val word = backStackEntry.arguments?.getString("email")
            val pkgId = backStackEntry.arguments?.getInt("pkgId")

            PackageDetailScreen(pkgId!!, word!!, navHostController)
        }


        composable(
            route = "${Screen.viewWordDetails.route}/{pkgId}/{word}",
            arguments = listOf(navArgument("pkgId") { type = NavType.IntType })
        ) { backStackEntry ->
            val word = backStackEntry.arguments?.getString("word")
            val pkgId = backStackEntry.arguments?.getInt("pkgId")

            WordScreen(pkgId, word!!)
        }

        composable(route = "${Screen.AddEditPackage.route}?email={email}&pkgId={pkgId}",
            arguments = listOf(
                navArgument("email") { type = NavType.StringType; defaultValue = "" },
                navArgument("pkgId") { type = NavType.IntType; defaultValue = 0 }
            ))
        { backStackEntry ->
            val email = backStackEntry.arguments?.getString("email")
            val pkgId = backStackEntry.arguments?.getInt("pkgId")

            if (pkgId == 0)
                UpdatePackageCard(user = email, navController = navHostController)
            else
                UpdatePackageCard(user = email, navController = navHostController, id = pkgId)
        }
    }
}

