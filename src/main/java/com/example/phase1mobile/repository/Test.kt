package com.example.phase1mobile.repository

import android.content.Context
import android.os.Build
import android.provider.ContactsContract.Contacts.Photo
import androidx.annotation.RequiresApi
import androidx.compose.ui.platform.LocalContext
import com.example.phase1mobile.model.Definition
import com.example.phase1mobile.model.LearningPackage
import com.example.phase1mobile.model.Rating
import com.example.phase1mobile.model.Resource
import com.example.phase1mobile.model.ResourceTypeEnum
import com.example.phase1mobile.model.Sentence
import com.example.phase1mobile.model.User
import com.example.phase1mobile.model.Word
import java.time.LocalDateTime


@RequiresApi(Build.VERSION_CODES.O)
fun main(){

//creating a few testing instances of learning pacakge to test package repo on

// Define a Rating class instance
    val rating1 = Rating(
        "Great package!",
        LocalDateTime.now(),
        "User1",
        5
    )

// Define a Word class instance
    val word1 = Word("Apple")
    word1.addDefinition(Definition("A fruit with a red or green skin and a crisp texture.", "Wikepedia"))
    word1.addSentence(Sentence("I love eating apples."))
    word1.addResource(Resource("https://example.com/apple-image.jpg", "Image of an apple", "Apple resource",
        ResourceTypeEnum.Video
    ))

// Create LearningPackage instances using the Rating and Word instances
    val package1 = LearningPackage(
        1,
        "Author 1",
        "Category A",
        "Description for Package 1",
        "iconUrl1",
        arrayOf("fruit", "food"),
        "English",
        "2023-10-29",
        "Beginner",
        "Package 1",
        1,
        mutableListOf(word1),
        mutableListOf(rating1)
    )

    val package2 = LearningPackage(
        2,
        "Author 2",
        "Category B",
        "Description for Package 2",
        "iconUrl2",
        arrayOf("language", "vocabulary"),
        "Spanish",
        "2023-10-30",
        "Easy",
        "Package 2",
        1,
        mutableListOf(
            Word("Hola"),
            Word("Amigo")
        ),
        mutableListOf(
            Rating("Nice package", LocalDateTime.now(), "User2", 4),
            Rating("Very helpful", LocalDateTime.now(), "User3", 5)
        )
    )
    val rating3 = Rating(
        "Informative content",
        LocalDateTime.now(),
        "User4",
        4
    )

// Define a Word class instance for package 3
    val word3 = Word("Mountain")
    word3.addDefinition(Definition("A large natural elevation of the earth's surface.", "Google Scholar"))
    word3.addSentence(Sentence("They climbed the mountain together."))
    word3.addResource(Resource("https://example.com/mountain-view.jpg", "Scenic mountain view", "Mountain", ResourceTypeEnum.Photo))

    val package3 = LearningPackage(
        3,
        "Author 3",
        "Category C",
        "Description for Package 3",
        "iconUrl3",
        arrayOf("nature", "adventure"),
        "English",
        "2023-10-31",
        "Average",
        "Package 3",
        1,
        mutableListOf(word3),
        mutableListOf(rating3)
    )

// Define a Rating class instance for package 4
    val rating4 = Rating(
        "Excellent package!",
        LocalDateTime.now(),
        "User5",
        5
    )

// Define a Word class instance for package 4
    val word4 = Word("Science")
    word4.addDefinition(Definition("The intellectual and practical activity encompassing the systematic study of the structure and behavior of the physical and natural world.", "Wikepedia"))
    word4.addSentence(Sentence("I have a keen interest in science."))
    word4.addResource(Resource("https://example.com/science-lab.jpg", "Science laboratory image", "Science lab", ResourceTypeEnum.Photo))

    var package4 = LearningPackage(
        4,
        "Author 4",
        "Category D",
        "Description for Package 4",
        "iconUrl4",
        arrayOf("education", "research"),
        "English",
        "2023-11-01",
        "Beginner",
        "Package 4",
        1,
        mutableListOf(word4),
        mutableListOf(rating4)
    )
    val rating5 = Rating(
        "Good for beginners",
        LocalDateTime.now(),
        "User6",
        4
    )

// Define a Word class instance for package 5
    val word5 = Word("Computer")
    word5.addSentence(Sentence("I use a computer for work and entertainment."))

    val package5 = LearningPackage(
        5,
        "Author 5",
        "Category E",
        "Description for Package 5",
        "iconUrl5",
        arrayOf("technology", "computing"),
        "English",
        "2023-11-02",
        "Beginner",
        "Package 5",
        1,
        mutableListOf(word5),
        mutableListOf(rating5)
    )

//Few instances of user class
// Create a User instance for a regular user
    val user1 = User(
        "John",
        "Doe",
        "john.doe@example.com",
        "password123",
        "https://example.com/user1.jpg",
        "User"
    )

// Create a User instance for an administrator
    val adminUser = User(
        "Admin",
        "User",
        "admin@example.com",
        "adminPassword",
        "https://example.com/admin.jpg",
        "Administrator"
    )

// Create a User instance with no profile photo
    val user2 = User(
        "Jane",
        "Smith",
        "jane.smith@example.com",
        "password456",
        "",
        "User"
    )

// Create a User instance with multiple ratings
    val user3 = User(
        "Alice",
        "Johnson",
        "alice.johnson@example.com",
        "securePassword",
        "https://example.com/alice.jpg",
        "User"
    )
    user3.ratings = mutableListOf(
        Rating("Great content", LocalDateTime.now(), "User1", 5),
        Rating("Very informative", LocalDateTime.now(), "User2", 4)
    )
    val user4 = User(
        "Sarah",
        "Wilson",
        "sarah.wilson@example.com",
        "securePassword123",
        "https://example.com/sarah.jpg",
        "User"
    )


    var packages= mutableListOf<LearningPackage>(package1,package2, package3,package4)
    println("*****Testing the packages are in the list by printing their titles*****")
    packages.forEach{ println(it.title) }
    PackageRepo.packages=packages
    println("*****Testing getFilteredPackages with just level*****")
    println("Packages with level Beginner are ${PackageRepo.getFilteredPackages(name = "", level = "Beginner" ).map { it.title }}")
    println("*****Testing getFilteredPackages with level and a word inside*****")
    println("Packages with level Beginner and include the word Science are ${PackageRepo.getFilteredPackages(name = "Science", level = "Beginner" ).map { it.title }}")
    println("*****Testing getPackageById*****")
    println("******Package with id 4 is ${PackageRepo.getPackageById(4)?.title}******")

//    println("*****Testing getLevels*****")
//    println("The levels are: ${PackageRepo.getLevels()}")
//commented out because cannot test without context
    println("*****Testing findWord*****")
    println("******Word with text Science in package 4 is ${PackageRepo.findWord(4, "Science")?.text}******")
    println("*****Testing updatePackageList*****")
    //updating package 4
    package4.title = "Updated package 4"
    PackageRepo.updatePackageList(package4)
    println("******Packages list after updating package 4: ******")
    packages.forEach{ println(it.title)}
    println("*****Testing adding a package to the package list*****")
    PackageRepo.updatePackageList(package5)
    println("******Packages list after adding package 5: ******")
    packages.forEach{ println(it.title)}


    var users = mutableListOf<User>(user1,user2,user3)
    UserRepo.users = users
    println("******Checking users are added to the list******")
    users.forEach{println(it.firstName)}
    println("******Testing wrong email and pass******")
    println("${UserRepo.checkUser("Wrong email", "Wrong pass")}")
    println("******Testing correct email and wrong pass******")
    println("${UserRepo.checkUser("jane.smith@example.com", "password123")}")
    println("******Testing correct email and correct pass, should return their role******")
    println("${UserRepo.checkUser("jane.smith@example.com", "password456")}")
    println("*******Testing adding user, list after user added: *****")
    UserRepo.addUser(user4)
    users.forEach{println(it.firstName)}


}