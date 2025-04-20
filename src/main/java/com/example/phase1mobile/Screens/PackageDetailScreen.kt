package com.example.phase1mobile.Screens

import android.content.Context
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.phase1mobile.components.WordList
import com.example.phase1mobile.repository.PackageRepo

@Composable
fun PackageDetailScreen(id: Int, email: String, navController: NavController) {
    val learningPackage = PackageRepo.getPackageById(id)

    Column(modifier = Modifier.fillMaxWidth()) {
        Column(modifier = Modifier.padding(10.dp, 20.dp)) {
            Row(modifier = Modifier.fillMaxWidth()) {
                Text(
                    text = learningPackage?.title!!,
                    style = MaterialTheme.typography.displayMedium,
                    fontWeight = FontWeight.Medium,
                    fontSize = 40.sp,
                    modifier = Modifier
                        .fillMaxWidth(),
                    color = MaterialTheme.colorScheme.primary
                )
            }
            Spacer(modifier = Modifier.height(5.dp))
            Card {
                Text(
                    text = "#${learningPackage?.category}",
                    style = MaterialTheme.typography.bodyMedium,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(6.dp, 3.dp)
                )
            }
            Text(
                text = "Description: ${learningPackage?.description}",
                fontSize = 16.sp
            )
            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(0.dp, 10.dp)
            ) {
                Text(
                    text = "Created by ${learningPackage?.author}",
                    fontSize = 12.sp,
                    color = Color.Gray
                )

                Text(
                    text = "Last Updated: ${learningPackage?.lastUpdatedDate}",
                    fontSize = 12.sp,
                    color = Color.Gray
                )
            }
            TextButton(
                onClick = {
                    navController.navigate("${Screen.PackagesList.route}/${email}") {
                        launchSingleTop = true
                    }
                },
            )
            {
                Text(
                    text = "Go back to Packages List",
                    fontWeight = FontWeight.ExtraBold,
                    textAlign = TextAlign.Left
                )
            }
        }
        Surface(
            color = MaterialTheme.colorScheme.primary, modifier = Modifier
                .fillMaxSize()
                .padding(top = 20.dp)
        ) {

            WordList(id, words = learningPackage?.words!!, navController = navController)
        }
    }

}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun PackageScreenPreview() {
    var context: Context = LocalContext.current
    val pk = PackageRepo.getPackages(context)[2]
    //PackageDetailScreen(pk)
}