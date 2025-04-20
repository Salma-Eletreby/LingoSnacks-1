package com.example.phase1mobile.components

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Card
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.blur
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.example.phase1mobile.R
import com.example.phase1mobile.Screens.Screen
import com.example.phase1mobile.model.LearningPackage
import com.example.phase1mobile.repository.PackageRepo
import com.example.phase1mobile.repository.UserRepo
import com.example.phase1mobile.ui.theme.Phase1MobileTheme

@Composable
fun PackageCard(
    navController: NavController,
    packagee: LearningPackage,
    onDeletePackage: (LearningPackage) -> Unit,
    user: String = "",
    modifier: Modifier = Modifier
) {
    Column(
        modifier = Modifier
            .padding(horizontal = 10.dp)
    ) {
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(5.dp)
                .clickable {
                    navController.navigate("${Screen.PackageDetails.route}/${user}/${packagee.packageId}") {
                        launchSingleTop = true
                    }
                }
        ) {
            Box(
                Modifier
                    .height(200.dp)
                    .fillMaxWidth()
            ) {
                Image(
                    painter = painterResource(id = R.drawable.packagecard),
                    modifier = Modifier
                        .fillMaxSize()
                        .blur(4.dp),
                    contentDescription = "Background Image",
                    contentScale = ContentScale.Crop
                )
                Row(
                    horizontalArrangement = Arrangement.End,
                    modifier = modifier
                        .fillMaxWidth()
                        .padding(5.dp)
                ) {
                    if (packagee.author == user) {
                        Icon(
                            imageVector = Icons.Default.Edit,
                            contentDescription = "Edit",
                            modifier = Modifier
                                .align(Alignment.Top)
                                .clickable {
                                    val route =
                                        "${Screen.AddEditPackage.route}?email=${user}&pkgId=${packagee.packageId}"
                                    Log.d("test", "$route")
                                    navController.navigate(route) {
                                        launchSingleTop = true
                                    }
                                },
                            tint = Color.Green
                        )
                        Icon(
                            imageVector = Icons.Default.Delete,
                            contentDescription = "Delete",
                            modifier = Modifier
                                .align(Alignment.Top)
                                .clickable {
                                    onDeletePackage(packagee)
                                    PackageRepo.packages.remove(packagee)
                                    Log.d("Hello", "${PackageRepo.packages}")
                                },
                            tint = Color.Red
                        )
                    }
                }
                Column(
                    modifier = modifier
                        .padding(10.dp)
                        .fillMaxWidth()
                        .height(200.dp)
                ) {
                    Row {
                        AsyncImage(
                            model = packagee.iconUrl,
//                            placeholder = painterResource(id = R.drawable.words),
                            error = painterResource(id = R.drawable.words),
                            contentDescription = " package icon",
                            contentScale = ContentScale.Crop,
                            modifier = Modifier
                                .size(50.dp)
                                .clip(CircleShape)
                        )
                        Column(
                            modifier = modifier.padding(horizontal = 10.dp)
                        ) {
                            Text(
                                text = "${packagee.title}",
                                fontWeight = FontWeight.Bold,
                                style = MaterialTheme.typography.titleLarge,
                            )
                            //Spacer(modifier = Modifier.height(2.dp))
                            Text(
                                text = "Author: ${packagee.author}",
                                fontWeight = FontWeight.Bold,
                                style = MaterialTheme.typography.titleMedium,
                            )
                        }
                    }
                    Spacer(modifier = Modifier.height(5.dp))
                    Text(
                        text = "Category: ${packagee.category}",
                        fontWeight = FontWeight.Bold,
                        style = MaterialTheme.typography.titleMedium,
                    )
                    Text(
                        text = "Description: ${packagee.description}",
                        fontWeight = FontWeight.Bold,
                        style = MaterialTheme.typography.titleMedium,
                    )

                }
            }

        }
    }
}

@Composable
fun PackageList(
    navController: NavController,
    packages: List<LearningPackage>,
    deletePacakage: (LearningPackage) -> Unit,
    user: String,
    modifier: Modifier = Modifier
) {
    LazyColumn(
        modifier = modifier
    ) {
        items(packages) {
            PackageCard(navController, it, deletePacakage, user)
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopBar(
    modifier: Modifier = Modifier,
    user: String,
    filterBy: (String, String) -> Unit,
    navController: NavController
) {
    var query by rememberSaveable {
        mutableStateOf("")
    }
    var showMenu by rememberSaveable {
        mutableStateOf(false)
    }
    val levels = PackageRepo.getLevels(LocalContext.current)

    var selectedLevel by rememberSaveable { mutableStateOf("All") }
    var logOut by rememberSaveable { mutableStateOf(false) }

    Row(
        modifier = modifier
            .padding(horizontal = 10.dp)
    ) {
        TextField(
            value = query,
            onValueChange = {
                query = it
                filterBy(query, selectedLevel)
            },
            leadingIcon = {
                Icon(
                    imageVector = Icons.Default.Search,
                    contentDescription = "Search"
                )
            },
            trailingIcon = {
                if (query.isNotEmpty())
                    Icon(
                        imageVector = Icons.Default.Close,
                        contentDescription = "Close",
                        modifier = modifier.clickable {
                            query = ""
                            filterBy(query, selectedLevel)
                        }
                    )
            },
            modifier = Modifier.weight(1f)

        )

        Box(
            modifier = Modifier.width(140.dp)
        ) {
            TextField(
                value = selectedLevel,
                onValueChange = { selectedLevel = it },
                trailingIcon = {
                    Icon(
                        imageVector = Icons.Default.ArrowDropDown,
                        contentDescription = "Filter",
                        modifier = modifier
                            .clickable {
                                Log.d("Hello", levels.toString())
                                showMenu = true
                            }
                    )
                },
                readOnly = true
            )
            DropdownMenu(
                expanded = showMenu,
                onDismissRequest = { showMenu = false }
            )
            {
                DropdownMenuItem(text = { Text(text = "All") }, onClick = {
                    showMenu = false
                    selectedLevel = "All"
                    filterBy(query, "All")
                })
                levels.forEach {
                    DropdownMenuItem(text = { Text(text = it) }, onClick = {
                        showMenu = false
                        selectedLevel = it
                        filterBy(query, it)
                    })
                }
            }
        }
        Box(modifier = Modifier.background(Color(231, 223, 236))) {
            AsyncImage(
                model = UserRepo.getProfile(user),
//                placeholder = painterResource(R.drawable.appicon),
                error = painterResource(R.drawable.appicon),
                contentDescription = "Profile pic",
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .size(55.dp)
                    .clip(CircleShape)
                    .clickable {
                        logOut = !logOut
                    }
            )
            DropdownMenu(
                expanded = logOut,
                onDismissRequest = { logOut = false }
            )
            {
                DropdownMenuItem(text = { Text(text = "Log Out") }, onClick = {
                    logOut = false
                    navController.navigate(Screen.LoginScreen.route) {
                        launchSingleTop = true
                        popUpTo(Screen.LoginScreen.route)
                    }
                })
            }
        }
    }
}

@Preview
@Composable
fun PackageCardPreview(
    modifier: Modifier = Modifier
) {
    val packagee = LearningPackage(
        packageId = 1,
        author = "Fatma",
        category = "Names",
        description = "A list of tests that we can find in town",
        language = "English",
        lastUpdatedDate = "20/11/02",
        level = "hard",
        title = "Tests",
        version = 1,
        words = mutableListOf()
    )


    //   PackageCard(packagee = packagee, onDeletePackage = {}, "Fatma")
}

@Preview
@Composable
fun PackageListPreview() {
    var packages = PackageRepo.getPackages(LocalContext.current)
    Phase1MobileTheme {
        //  PackageList(packages, {}, "")
    }
}

@Preview
@Composable
fun TopBarPreview() {
    PackageRepo.getPackages(LocalContext.current)
    Phase1MobileTheme {
//        TopBar(filterBy = { query, level -> })
    }
}