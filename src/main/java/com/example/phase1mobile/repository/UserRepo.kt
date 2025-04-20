package com.example.phase1mobile.repository


import android.content.Context
import android.util.Log
import com.example.phase1mobile.model.User
import kotlinx.serialization.json.Json

object UserRepo {
    var users = mutableListOf<User>()

    fun getUsers(context: Context): MutableList<User> {
        if (users.isEmpty()) {
            val userJson = context.assets
                .open("users.json")
                .bufferedReader()
                .use { it.readText() }

            users = Json { ignoreUnknownKeys = true }.decodeFromString(userJson)
        }
        return users
    }

    fun checkUser(email: String, password: String): String {
        var result = "Incorrect email or password"

        var found = users.find { it.email.equals(email) && it.password.equals(password) }

        if (found != null)
            result = found.role

        return result
    }

    fun addUser(new: User): String {
        if (users.any { it.email.equals(new.email, ignoreCase = true) })
            return "repeat email"
        else {
            users.add(new)
            return "success"
        }
    }

    fun getProfile(user: String): String {
        return users.find { it.email.equals(user) }?.photoUri ?: ""
    }
}

