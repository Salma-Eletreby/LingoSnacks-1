package com.example.phase1mobile.components

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddCircle
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import coil.compose.AsyncImage
import com.example.phase1mobile.R
import com.example.phase1mobile.Screens.Screen
import com.example.phase1mobile.model.Definition
import com.example.phase1mobile.model.LearningPackage
import com.example.phase1mobile.model.Resource
import com.example.phase1mobile.model.ResourceTypeEnum
import com.example.phase1mobile.model.Sentence
import com.example.phase1mobile.model.Word
import com.example.phase1mobile.repository.PackageRepo
import java.time.LocalDate

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DefinitionCard(
    definition: MutableState<Definition>,
    text: MutableState<String>,
    source: MutableState<String>,
    index: Int,
    onDelete: (Definition) -> Unit,
    onUpdateText: (Int, Definition) -> Unit,
    modifier: Modifier = Modifier
) {
    var updatedDefinition = Definition(text.value, source.value)
    Card(
        colors = CardDefaults.cardColors(
            containerColor = Color.Transparent
        ),
        modifier = modifier
            .fillMaxWidth()
            .padding(vertical = 5.dp)
    ) {
        Column(
            modifier = Modifier
                .padding(6.dp)
        ) {
            Column(modifier = Modifier.padding(bottom = 5.dp)) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                        .padding(0.dp, 5.dp)
                        .fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        text = "Defintion",
                        style = MaterialTheme.typography.titleMedium
                    )
                    Spacer(modifier = Modifier.weight(1f))

                    Icon(imageVector = Icons.Default.Clear, contentDescription = null,
                        modifier.clickable {
                            onDelete(definition.value)
                        })
                }

                OutlinedTextField(
                    value = text.value,
                    onValueChange = {
                        text.value = it
                        updatedDefinition.text = it
                        onUpdateText(index, updatedDefinition)
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 3.dp),
                    textStyle = MaterialTheme.typography.bodyMedium,
                    singleLine = true,
                    label = { Text("Text") }
                )
                OutlinedTextField(
                    value = source.value,
                    onValueChange = {
                        source.value = it
                        updatedDefinition.source = it
                        onUpdateText(index, updatedDefinition)
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 3.dp),
                    textStyle = MaterialTheme.typography.bodyMedium,
                    singleLine = true,
                    label = { Text("Source") }
                )
            }
        }
    }
}

@Composable
fun DefinitionList(
    definitionReceived: MutableState<MutableList<Definition>>,
    onAdd: (Definition) -> Unit,
    delete: (Definition) -> Unit,
    updateText: (Int, Definition) -> Unit
) {

    Column {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(
                text = "Definitions",
                fontWeight = FontWeight.Bold,
                style = MaterialTheme.typography.titleLarge,
                color = Color.DarkGray
            )
            Icon(
                imageVector = Icons.Default.AddCircle,
                contentDescription = "Add definition",
                modifier = Modifier
                    .clickable {
                        onAdd(Definition("", ""))
                    },
                tint = Color.DarkGray
            )
        }
        definitionReceived.value.forEachIndexed { index, definition ->
            DefinitionCard(
                definition = mutableStateOf(definition),
                text = mutableStateOf(definition.text),
                source = mutableStateOf(definition.source),
                index = index,
                onDelete = delete,
                onUpdateText = updateText
            )
        }

    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ResourceCard(
    resource: MutableState<Resource>,
    title: MutableState<String>,
    url: MutableState<String>,
    type: MutableState<ResourceTypeEnum>,
    extension: MutableState<String>,
    onUpdate: (Int, Resource) -> Unit,
    index: Int,
    onDeleteR: (Resource) -> Unit
) {
    var updatedResource = Resource(extension.value, url.value, title.value, type.value)
    var showMenu by remember {
        mutableStateOf(false)
    }
    val levels = listOf(
        ResourceTypeEnum.Photo.toString(),
        ResourceTypeEnum.Video.toString(),
        ResourceTypeEnum.Website.toString()
    )

    Card {
        Column {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .padding(bottom = 5.dp)
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(text = "Resource", style = MaterialTheme.typography.titleSmall)
                Icon(
                    imageVector = Icons.Default.Close,
                    contentDescription = "Delete",
                    modifier = Modifier
                        .clickable {
                            onDeleteR(resource.value)
                        },
                    tint = Color.Gray
                )
            }

            OutlinedTextField(
                value = title.value,
                onValueChange = {
                    title.value = it
                    updatedResource.title = it
                    onUpdate(index, updatedResource)
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 3.dp),
                textStyle = MaterialTheme.typography.bodyMedium,
                singleLine = true,
                label = { Text("Title") }
            )
            OutlinedTextField(
                value = url.value,
                onValueChange = {
                    url.value = it
                    updatedResource.url = it
                    onUpdate(index, updatedResource)
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 3.dp),
                textStyle = MaterialTheme.typography.bodyMedium,
                singleLine = true,
                label = { Text("URL") }
            )
            OutlinedTextField(
                value = extension.value,
                onValueChange = {
                    extension.value = it
                    updatedResource.extension = it
                    onUpdate(index, updatedResource)
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 3.dp),
                textStyle = MaterialTheme.typography.bodyMedium,
                singleLine = true,
                label = { Text("Extension") }
            )
            Box(modifier = Modifier.fillMaxWidth()){
                OutlinedTextField(
                    value = type.value.toString(),
                    onValueChange = { string ->
                        type.value =
                            ResourceTypeEnum.values()
                                .find { it.name.equals(string, ignoreCase = true) }
                                ?: ResourceTypeEnum.Photo
                    },
                    label = { Text("Type") },
                    trailingIcon = {
                        Icon(
                            imageVector = Icons.Default.ArrowDropDown,
                            contentDescription = "Choosing",
                            modifier = Modifier
                                .clickable {
                                    showMenu = true
                                }
                        )
                    },
                    readOnly = true,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 3.dp)
                )
                DropdownMenu(
                    expanded = showMenu,
                    onDismissRequest = { showMenu = false },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(10.dp)
                )
                {
                    levels.forEach { string ->
                        DropdownMenuItem(text = { Text(text = string) }, onClick = {
                            showMenu = false
                            type.value = ResourceTypeEnum.values()
                                .find { it.name.equals(string, ignoreCase = true) }
                                ?: ResourceTypeEnum.Photo
                            updatedResource.type = ResourceTypeEnum.values()
                                .find { it.name.equals(string, ignoreCase = true) }
                                ?: ResourceTypeEnum.Photo
                            onUpdate(index, updatedResource)
                        })
                    }
                }
            }

        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SentenceCard(
    sentenceReceived: MutableState<Sentence>,
    text: MutableState<String>,
    index: Int,
    onDelete: (Sentence) -> Unit,
    onUpdate: (Int, Sentence) -> Unit,
    resources: MutableState<MutableList<Resource>>,
    modifier: Modifier = Modifier
) {
    var updatedSentence = Sentence(text.value, resources.value)

    Card(
        modifier = modifier
            .fillMaxWidth()
            .padding(vertical = 5.dp)
    ) {
        Column(
            modifier = Modifier
                .padding(6.dp)
        ) {
            Column(modifier = Modifier.padding(bottom = 5.dp)) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                        .padding(5.dp)
                        .fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        text = "Sentence",
                        style = MaterialTheme.typography.titleMedium
                    )
                    Icon(
                        imageVector = Icons.Default.Close,
                        contentDescription = "Delete",
                        modifier = Modifier
                            .clickable {
                                onDelete(sentenceReceived.value)
                            },
                        tint = Color.Gray
                    )
                }

                OutlinedTextField(
                    value = text.value,
                    onValueChange = {
                        text.value = it
                        updatedSentence.text = it
                        onUpdate(index, updatedSentence)
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 3.dp),
                    textStyle = MaterialTheme.typography.bodyMedium,
                    singleLine = true,
                    label = { Text("Source") }
                )
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                        .fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        text = "Resources",
                        style = MaterialTheme.typography.titleLarge,
                        fontWeight = FontWeight.Bold
                    )

                    Icon(
                        imageVector = Icons.Default.AddCircle,
                        contentDescription = "Add",
                        modifier = Modifier
                            .clickable {
                                resources.value = resources.value
                                    .toMutableList()
                                    .also { it.add(0, Resource(
                                        "",
                                        "",
                                        "",
                                        ResourceTypeEnum.Photo
                                    )) }
                                sentenceReceived.value.resources = resources.value
                            },
                        tint = Color.DarkGray
                    )
                }

                resources.value.forEachIndexed { index, resource ->
                    ResourceCard(resource = mutableStateOf(resource),
                        title = mutableStateOf(resource.title),
                        url = mutableStateOf(resource.url),
                        type = mutableStateOf(resource.type),
                        extension = mutableStateOf(resource.extension),
                        index = index,
                        onUpdate = { index, resource ->
                            resources.value[index] = resource
                        },
                        onDeleteR = { resource ->
                            resources.value =
                                resources.value.toMutableList().also { it.remove(resource) }
                        })

                }
            }
        }
    }
}


@Composable
fun SentenceList(
    sentencesReceived: MutableState<MutableList<Sentence>>,
    onDelete: (Sentence) -> Unit,
    onAdd: (Sentence) -> Unit,
    onUpdate: (Int, Sentence) -> Unit,

    ) {

    Column {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(
                text = "Sentences",
                fontWeight = FontWeight.Bold,
                style = MaterialTheme.typography.titleLarge,
                color = Color.DarkGray
            )
            Icon(
                imageVector = Icons.Default.AddCircle,
                contentDescription = "Add Sentence",
                modifier = Modifier
                    .clickable {
                        onAdd(
                            Sentence(
                                "",
                                mutableListOf(
                                    Resource("", "", "", ResourceTypeEnum.Photo)
                                )
                            )
                        )

                    },
                tint = Color.DarkGray
            )
        }

        sentencesReceived.value.forEachIndexed { index, sentence ->
            SentenceCard(
                sentenceReceived = mutableStateOf(sentence),
                text = mutableStateOf(sentence.text),
                index = index,
                onDelete = onDelete,
                onUpdate = onUpdate,
                resources = mutableStateOf(sentence.resources)
            )

        }
    }


}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UpdateWordCard(
    onDelete: (Int) -> Unit,
    onUpdate: (Int, Word) -> Unit,
    index: Int,
    definitions: MutableState<MutableList<Definition>>,
    sentences: MutableState<MutableList<Sentence>>,
    text: MutableState<String>
) {
    var updatedWord = Word("${text.value}")
    updatedWord.definitions = definitions.value
    updatedWord.sentences = sentences.value
    Card {
        Column(
            modifier = Modifier
                .padding(10.dp)
                .background(Color.Transparent)
        ) {
            Row(
                horizontalArrangement = Arrangement.End,
                modifier = Modifier
                    .fillMaxWidth()
            ) {
                Icon(
                    imageVector = Icons.Default.Delete,
                    contentDescription = "Delete",
                    modifier = Modifier
                        .align(Alignment.Top)
                        .clickable {

                            onDelete(index)
                        },
                    tint = Color.Red
                )
            }
            Column(
                modifier = Modifier
            ) {
                OutlinedTextField(
                    value = text.value,
                    onValueChange = {
                        text.value = it
                        updatedWord.text = it
                        onUpdate(index, updatedWord)
                    },
                    label = { Text("Word") },
                    modifier = Modifier
                        .fillMaxWidth(),
                )
            }
            PackageRepo.getPackages(LocalContext.current)
            DefinitionList(definitionReceived = definitions,
                delete = { d ->
                    definitions.value = definitions.value.toMutableList().also { it.remove(d) }
                    updatedWord.definitions = definitions.value
                    onUpdate(index, updatedWord)
                }, onAdd = { d ->
                    definitions.value = definitions.value.toMutableList().also { it.add(d) }
                    updatedWord.definitions = definitions.value
                    onUpdate(index, updatedWord)
                }, updateText = { index, d ->
                    definitions.value[index] = d
                    updatedWord.definitions = definitions.value
                    onUpdate(index, updatedWord)
                })

            SentenceList(sentencesReceived = sentences,
                onDelete = { s ->
                    sentences.value = sentences.value.toMutableList().also { it.remove(s) }
                    updatedWord.sentences = sentences.value
                    onUpdate(index, updatedWord)
                }, onAdd = { s ->
                    sentences.value = sentences.value.toMutableList().also { it.add(s) }
                    updatedWord.sentences = sentences.value
                    onUpdate(index, updatedWord)
                }, onUpdate = { index, s ->
                    sentences.value[index] = s
                    updatedWord.sentences = sentences.value
                    onUpdate(index, updatedWord)
                })
        }
    }

}

@RequiresApi(Build.VERSION_CODES.O)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UpdatePackageCard(
    id: Int? = 0,
    user: String? = "",
    navController: NavController,
    modifier: Modifier = Modifier
) {
    var context = LocalContext.current
    var packagee: LearningPackage = LearningPackage(
        PackageRepo.getNewID(context),
        user!!,
        "",
        "",
        "",
        emptyArray(),
        "",
        LocalDate.now().toString(),
        "",
        "",
        0,
        mutableListOf<Word>(),
        mutableListOf()
    )

    if (id != null && id > 0) {
        packagee = PackageRepo.getPackageById(id)!!
    }
    PackageRepo.getPackages(LocalContext.current)
    val levels = PackageRepo.getLevels(LocalContext.current)
    var title by rememberSaveable { mutableStateOf(packagee.title) }
    var version by rememberSaveable { mutableStateOf(packagee.version + 1) }
    var description by rememberSaveable { mutableStateOf(packagee.description) }
    var language by rememberSaveable { mutableStateOf(packagee.language) }
    var category by rememberSaveable { mutableStateOf(packagee.category) }
    var selectedLevel by rememberSaveable { mutableStateOf(packagee.level) }
    var iconURL by rememberSaveable { mutableStateOf(packagee.iconUrl) }
    var words by rememberSaveable { mutableStateOf(packagee.words) }
    var showMenu by rememberSaveable {
        mutableStateOf(false)
    }

    Column(
        modifier = modifier
            .padding(horizontal = 5.dp)
            .verticalScroll(rememberScrollState())
            .background(Color(254, 235, 205))
    ) {
        Box(
            modifier = modifier.fillMaxSize()
        ) {
            Column(
                modifier = modifier
                    .padding(10.dp)
                    .fillMaxWidth()
                    .fillMaxHeight()
            ) {
                Row(
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "Package Details",
                        fontWeight = FontWeight.Bold,
                        style = MaterialTheme.typography.titleLarge,
                    )
                    Spacer(modifier = Modifier.weight(1f))
                    AsyncImage(
                        model = packagee.iconUrl,
                        error = painterResource(id = R.drawable.words),
                        contentDescription = " package icon",
                        contentScale = ContentScale.Crop,
                        modifier = Modifier
                            .size(50.dp)
                            .clip(CircleShape)
                    )
                }
                Row(
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "Author: ${user}",
                        fontWeight = FontWeight.Bold,
                        style = MaterialTheme.typography.titleMedium
                    )
                    Spacer(modifier = Modifier.weight(1f))
                    Text(
                        text = "ID: ${packagee.packageId}",
                        fontWeight = FontWeight.Bold,
                        style = MaterialTheme.typography.titleMedium,
                        modifier = modifier
                            .padding(end = 8.dp)
                    )
                }
                Row(
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "Version: ${version}",
                        fontWeight = FontWeight.Bold,
                        style = MaterialTheme.typography.titleMedium
                    )
                    Spacer(modifier = Modifier.weight(1f))
                }
                OutlinedTextField(
                    value = title,
                    onValueChange = {
                        title = it
                    },
                    label = { Text("Title") },
                    modifier = modifier
                        .fillMaxWidth()
                        .padding(10.dp),
                )
                Spacer(modifier = Modifier.height(5.dp))
                OutlinedTextField(
                    value = language,
                    onValueChange = {
                        language = it
                    },
                    label = { Text("Language") },
                    modifier = modifier
                        .fillMaxWidth()
                        .padding(10.dp),
                )
                Spacer(modifier = Modifier.height(5.dp))
                OutlinedTextField(
                    value = description,
                    onValueChange = {
                        description = it
                    },
                    label = { Text("Description") },
                    modifier = modifier
                        .fillMaxWidth()
                        .padding(10.dp),
                )
                Spacer(modifier = Modifier.height(5.dp))
                OutlinedTextField(
                    value = category,
                    onValueChange = {
                        category = it
                    },
                    label = { Text("Category") },
                    modifier = modifier
                        .fillMaxWidth()
                        .padding(10.dp),
                )
                Spacer(modifier = Modifier.height(5.dp))
                OutlinedTextField(
                    value = iconURL,
                    onValueChange = {
                        iconURL = it
                    },
                    label = { Text("Package Icon URL") },
                    modifier = modifier
                        .fillMaxWidth()
                        .padding(10.dp),
                )
                Spacer(modifier = Modifier.height(5.dp))
                OutlinedTextField(
                    value = selectedLevel,
                    onValueChange = {
                        selectedLevel = it
                    },
                    label = { Text("Level") },
                    trailingIcon = {
                        Icon(
                            imageVector = Icons.Default.ArrowDropDown,
                            contentDescription = "Choosing",
                            modifier = modifier
                                .clickable {
                                    showMenu = true
                                }
                        )
                    },
                    readOnly = true,
                    modifier = modifier
                        .fillMaxWidth()
                        .padding(10.dp),
                )
                DropdownMenu(
                    expanded = showMenu,
                    onDismissRequest = { showMenu = false },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(10.dp)
                )
                {
                    levels.forEach {
                        DropdownMenuItem(text = { Text(text = it) }, onClick = {
                            showMenu = false
                            selectedLevel = it
                        })
                    }
                }
                Spacer(modifier = Modifier.height(5.dp))
                Row() {
                    Row(
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = "Words: Count = ${words.size}",
                            fontWeight = FontWeight.Bold,
                            style = MaterialTheme.typography.titleLarge,
                        )
                        Spacer(modifier = Modifier.weight(1f))
                        Icon(
                            imageVector = Icons.Default.AddCircle,
                            contentDescription = "Add",
                            modifier = Modifier
                                .size(30.dp)
                                .clickable {
                                    words = words
                                        .toMutableList()
                                        .also { it.add(0, Word("")) }
                                },
                            tint = Color.DarkGray
                        )
                    }
                }
                if (words.isEmpty()) {
                    Spacer(modifier = Modifier.height(2.dp))
                    Text(
                        text = "You do not have any words in this package, please add using the add button.",
                        fontWeight = FontWeight.Bold,
                        style = MaterialTheme.typography.titleMedium,
                    )
                } else {
                    Column() {
                        words.forEachIndexed { index, word ->
                            UpdateWordCard(
                                { index ->
                                    words = words.toMutableList().also { it.removeAt(index) }
                                },
                                { index, updatedWord ->
                                    words =
                                        words.toMutableList().also { it.set(index, updatedWord) }
                                },
                                index,
                                mutableStateOf(word.definitions),
                                mutableStateOf(word.sentences),
                                mutableStateOf(word.text)
                            )
                        }
                    }
                }
                Button(
                    onClick = {
                        packagee.version = version
                        packagee.title = title
                        packagee.language = language
                        packagee.description = description
                        packagee.category = category
                        packagee.level = selectedLevel
                        packagee.words = words
                        packagee.lastUpdatedDate = LocalDate.now().toString()
                        packagee.iconUrl = iconURL
                        PackageRepo.updatePackageList(packagee)
                        navController.navigate("${Screen.PackagesList.route}/${user}") {
                            launchSingleTop = true
                        }
                    },
                    modifier = modifier
                        .fillMaxWidth()
                        .padding(10.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = MaterialTheme.colorScheme.primary
                    )
                ) {
                    Text("Save Changes")
                }
                TextButton(
                    onClick = {
                        navController.navigate("${Screen.PackagesList.route}/${user}") {
                            launchSingleTop = true
                        }
                    },
                    modifier = Modifier.fillMaxWidth()
                )
                {
                    Text(
                        text = "Discard Changes & Go Back",
                        fontWeight = FontWeight.ExtraBold,
                        textAlign = TextAlign.Left
                    )
                }
            }
        }
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Preview
@Composable
fun UpdatePackageCardPreview(
    modifier: Modifier = Modifier
) {
    //use for testing
    val navController = rememberNavController()
    val packagee = PackageRepo.getPackages(LocalContext.current).first()
    UpdatePackageCard(1, user = "Fatma", navController = navController)
}