package com.example.phase1mobile.repository

import android.content.Context
import android.content.Intent
import android.net.Uri
import com.example.phase1mobile.model.LearningPackage
import com.example.phase1mobile.model.Word

import kotlinx.serialization.json.Json

object PackageRepo {
    var packages = mutableListOf<LearningPackage>()
    fun getPackages(context: Context): List<LearningPackage> {
        if (packages.isEmpty()) {
            val packageJson = context.assets
                .open("packages.json")
                .bufferedReader()
                .use { it.readText() }
            println(packageJson)
            packages = Json.decodeFromString(packageJson)

        }
        return packages
    }

    fun getFilteredPackages(name: String, level: String = "All") = packages.filter {
        if (level.equals("All"))
            it.words.any { word -> word.text.contains(name, ignoreCase = true) }
        else {
            it.words.any { word ->
                word.text.contains(
                    name,
                    ignoreCase = true
                )
            } and it.level.contains(level, ignoreCase = true)
        }
    }

    fun getPackageById(id: Int): LearningPackage? {
        return packages.find { it.packageId == id }
    }

    fun getLevels(context: Context): List<String> {
        return packages.map { it.level }.distinct()
    }

    fun findWord(id: Int, word: String): Word? {
        return packages.find { it.packageId == id }?.words?.find { it.text == word }
    }

    fun getNewID(context: Context): Int {
        return getPackages(context).count() + 1
    }

    fun updatePackageList(packagee: LearningPackage) {
        val index = packages.indexOf(packagee)
        if (index >= 0)
            packages[index] = packagee
        else if (index == -1)
            packages.add(packagee)
    }

    fun openUri(context: Context, uri: String) {
        val intent = Intent(
            Intent.ACTION_VIEW,
            Uri.parse(uri)
        )
        context.startActivity(intent)
    }
}



