package com.example.phase1mobile.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.phase1mobile.Screens.Screen
import com.example.phase1mobile.Screens.WordScreen
import com.example.phase1mobile.model.Definition
import com.example.phase1mobile.model.Word

@Composable
fun WordCard(id: Int, word: Word, showWord: (Word) -> Unit, navController: NavController) {
    val defintions = word.definitions
    Card(
        shape = MaterialTheme.shapes.medium,
        //  shape = RoundedCornerShape(8.dp),
        modifier = Modifier
            .padding(10.dp, 5.dp, 10.dp, 10.dp)
            .fillMaxWidth()
            .height(150.dp)
            .clickable {
                navController.navigate("${Screen.viewWordDetails.route}/${id}/${word.text}") {
                    launchSingleTop = true
                }
            },
        //set card elevation of the card
        elevation = CardDefaults.cardElevation(
            defaultElevation = 10.dp,
        ),
        colors = CardDefaults.cardColors(containerColor = Color.White),
    ) {


        Column(modifier = Modifier.padding(16.dp)) {
            Text(
                text = "${word.text}",
                style = MaterialTheme.typography.titleLarge,
                color = Color.Black
            )
            Spacer(modifier = Modifier.height(9.dp))
            Text("Definition:", color = Color.Blue, fontWeight = FontWeight.Bold)
            Spacer(modifier = Modifier.height(5.dp))
            defintions.forEach {
                Text(
                    text = "-${it.text}",
                    //maxLines = 1,
                    color = Color.DarkGray,
                    overflow = TextOverflow.Ellipsis,
                    style = MaterialTheme.typography.bodySmall,
                    fontStyle = FontStyle.Italic
                )
                Spacer(modifier = Modifier.height(4.dp))
            }
        }

    }

}

@Composable
fun WordList(id: Int, words: List<Word>, navController: NavController) {
    Column {
        // Text(text = "Words",style = MaterialTheme.typography.headlineMedium,color=Color.Blue, modifier = Modifier.padding(5.dp))
        LazyColumn {
            item {
                Text(
                    "Practice words",
                    color = Color.White,
                    style = MaterialTheme.typography.headlineMedium,
                    modifier = Modifier.padding(10.dp, 15.dp, 5.dp, 5.dp)
                )
            }

            items(words) {
                WordCard(id, word = it, {}, navController = navController)
            }
        }
    }
}

@Preview
@Composable
fun WordCardPreview() {
    val word = Word("Apple")
    word.addDefinition(
        Definition(
            "he round fruit of a tree of the rose family, which typically has thin green or red skin and crisp flesh",
            "Cambridge Dictionary"
        )
    )
    word.addDefinition(
        Definition(
            "the tree bearing apples, with hard pale timber that is used in carpentry and to smoke food",
            "Cambridge Dictionary"
        )
    )
    WordScreen(1, "Mall")
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun WorListPreview() {
    val word1 = Word("Apple")
    word1.addDefinition(
        Definition(
            "he round fruit of a tree of the rose family, which typically has thin green or red skin and crisp flesh",
            "Cambridge Dictionary"
        )
    )
    word1.addDefinition(
        Definition(
            "the tree bearing apples, with hard pale timber that is used in carpentry and to smoke food",
            "Cambridge Dictionary"
        )
    )
    val word2 = Word("Corona Virus")
    word2.addDefinition(
        Definition(
            "A group of RNA viruses that cause diseases in mammals and birds",
            "Cambridge Dictionary"
        )
    )
    // word2.addDefinition(Definition("the tree bearing apples, with hard pale timber that is used in carpentry and to smoke food","Cambridge Dictionary"))
    val word3 = Word("Grapes")
    word3.addDefinition(
        Definition(
            "A berry (typically green, purple, or black) growing in clusters on a grapevine, eaten as fruit",
            "Cambridge Dictionary"
        )
    )
    word3.addDefinition(
        Definition(
            "A small, round, purple or pale green fruit that you can eat",
            "Cambridge Dictionary"
        )
    )
    var words = listOf<Word>(word1, word2, word3)
//    WordList(words = words)
}