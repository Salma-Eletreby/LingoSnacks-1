package com.example.phase1mobile.Screens

import android.annotation.SuppressLint
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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Card
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SmallFloatingActionButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.contentColorFor
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
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
import com.example.phase1mobile.components.PackageList
import com.example.phase1mobile.components.TopBar
import com.example.phase1mobile.model.LearningPackage
import com.example.phase1mobile.repository.PackageRepo
import com.example.phase1mobile.repository.PackageRepo.getLevels
import com.example.phase1mobile.repository.UserRepo
import com.example.phase1mobile.ui.theme.Phase1MobileTheme

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PackageScreen(
    navController: NavController,
    user: String, modifier: Modifier = Modifier
) {
    var context = LocalContext.current
    var packages by remember {
        mutableStateOf(PackageRepo.getPackages(context))
    }
    val filterBy: (String, String) -> Unit = { name, level ->
        packages = PackageRepo.getFilteredPackages(name, level)
    }
    Scaffold(
        topBar = {
            TopBar(
                modifier = modifier
                    .clip(
                        shape = RoundedCornerShape(bottomEnd = 30.dp, bottomStart = 30.dp)
                    ),
                user = user,
                //   .background(color =  Color(0xFFD2B48C))
                filterBy = filterBy,
                navController = navController
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                containerColor = MaterialTheme.colorScheme.primary,
                shape = MaterialTheme.shapes.large,
                onClick = {
                    navController.navigate("${Screen.AddEditPackage.route}?email=${user}") {
                        launchSingleTop = true
                    }
                })
            {
                Icon(
                    imageVector = Icons.Filled.Add,
                    contentDescription = null,
                    modifier = modifier.size(50.dp)
                )
            }
        },
        content = {
            PackageList(
                navController, packages,
                modifier = modifier.padding(it),
                deletePacakage = { deletePackage ->
                    packages = packages.toMutableList().also { it.remove(deletePackage) }
                },
                user = user
            )
        }
    )
}