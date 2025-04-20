package com.example.phase1mobile.Screens

import android.content.Context
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.blur
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.phase1mobile.R
import com.example.phase1mobile.repository.PackageRepo
import com.example.phase1mobile.ui.theme.wordFont

@Composable
fun WordScreen(pkgId: Int? = 0, word: String = "") {
    var context: Context = LocalContext.current
    val word = PackageRepo.findWord(pkgId!!, word)
    Surface(modifier = Modifier.fillMaxSize()) {
        Box {
            Image(
                painter = painterResource(id = R.drawable.packagecard),
                modifier = Modifier
                    .fillMaxSize()
                    .blur(4.dp),
                contentDescription = "Background Image",
                contentScale = ContentScale.FillBounds
            )

            Column(
                modifier = Modifier
                    .fillMaxWidth()
            ) {
                Text(
                    text = word?.text ?: "",
                    fontFamily = wordFont,
                    fontWeight = FontWeight.Medium,
                    fontSize = 40.sp,
                    modifier = Modifier
                        .padding(30.dp)
                        .fillMaxWidth(),
                    textAlign = TextAlign.Center
                )

                Spacer(modifier = Modifier.height(9.dp))
                Card(
                    colors = CardDefaults.cardColors(
                        containerColor = Color.White,
                    ), modifier = Modifier
                        .fillMaxWidth()
                        .padding(10.dp)
                ) {
                    Column(
                        modifier = Modifier
                            .padding(10.dp)
                            .fillMaxWidth()
                    ) {


                        Text(
                            "Definition",
                            color = Color.DarkGray,
                            fontWeight = FontWeight.Bold,
                            fontSize = 20.sp,
                            modifier = Modifier.padding(bottom = 10.dp)
                        )
                        Spacer(modifier = Modifier.height(5.dp))
                        var count = 1

                        word?.definitions?.forEach {
                            Text(
                                color = Color.Gray,
                                text = "${count} : ${it.text}",
                                style = MaterialTheme.typography.bodyMedium,
                                fontStyle = FontStyle.Italic,
                                modifier = Modifier.padding(start = 4.dp)
                            )
                            Spacer(modifier = Modifier.height(4.dp))
                            count++
                        }
                    }
                }
                Spacer(modifier = Modifier.height(20.dp))

                Card(
                    colors = CardDefaults.cardColors(
                        containerColor = Color.White,
                    ), modifier = Modifier
                        .fillMaxWidth()
                        .padding(10.dp)
                ) {
                    Column(
                        modifier = Modifier
                            .padding(10.dp)
                            .fillMaxWidth()
                    ) {


                        Text(
                            "Sentences",
                            color = Color.DarkGray,
                            fontWeight = FontWeight.Bold,
                            fontSize = 20.sp,
                            modifier = Modifier.padding(bottom = 10.dp)
                        )
                        Spacer(modifier = Modifier.height(5.dp))

                        var count = 1
                        word?.sentences?.forEach {
                            Text(
                                color = Color.Black,
                                text = "${count} : \"${it.text}\"",
                                style = MaterialTheme.typography.bodyMedium,
                                modifier = Modifier.padding(start = 4.dp)
                            )
                            Spacer(modifier = Modifier.height(3.dp))
                            count++
                            it.resources.forEach {
                                Text(
                                    text = "  ${it.url}",
                                    style = MaterialTheme.typography.bodySmall,
                                    modifier = Modifier
                                        .padding(start = 7.dp)
                                        .clickable {
                                            PackageRepo.openUri(context, uri = it.url)
                                        },
                                    textDecoration = TextDecoration.Underline,
                                    color = Color(0xff64B5F6)
                                )
                            }
                            Spacer(modifier = Modifier.height(10.dp))
                        }

                    }
                }
            }
        }
    }

}