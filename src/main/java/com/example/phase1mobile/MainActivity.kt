package com.example.phase1mobile

import android.content.Context
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.windowsizeclass.ExperimentalMaterial3WindowSizeClassApi
import androidx.compose.material3.windowsizeclass.calculateWindowSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.rememberNavController
import com.example.phase1mobile.Screens.LoginScreen
import com.example.phase1mobile.nav.MyNavHost
import com.example.phase1mobile.repository.PackageRepo
import com.example.phase1mobile.repository.UserRepo
import com.example.phase1mobile.ui.theme.Phase1MobileTheme

class MainActivity : ComponentActivity() {
    @OptIn(ExperimentalMaterial3WindowSizeClassApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val windowSize = calculateWindowSizeClass(this)
            Phase1MobileTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    val navController = rememberNavController()
                    MyNavHost(navHostController = navController)
//                    LoginScreen()
                }
            }
        }
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier,context:Context) {
    Box {
        Text(
            text = "Hello $name!",
            modifier = modifier
        )
        // var context: Context = LocalContext.current
        val packages= PackageRepo.getPackages(context)

        LazyColumn {
            items(packages){
                Text(it.author)
            }
        }
    }

}

@OptIn(ExperimentalMaterial3Api::class, ExperimentalMaterial3Api::class)
@Composable
fun Screen ( modifier: Modifier = Modifier){
    var context: Context = LocalContext.current
   // var productlsit by remember { mutableStateOf(ProductRepository.initProducts(context)) }
    var categories=   PackageRepo.getPackages(context).toMutableList()

    Phase1MobileTheme {
        Scaffold(

        ) {
           Column (Modifier.padding(it)){
              UserRepo.getUsers(context).forEach { Text(it.email) }
           }
            }
        }
    }
@Preview(showBackground = true)
@Composable
fun ProductScreenPreview() {
    Phase1MobileTheme {
        Screen()
    }
}

