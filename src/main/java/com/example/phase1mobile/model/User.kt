package com.example.phase1mobile.model

import kotlinx.serialization.Serializable

@Serializable
class User (
    val firstName:String,
    val lastName:String,
    val email:String,
    var password:String,
    var photoUri: String,
    val role:String
){
    var ratings:MutableList<Rating> = mutableListOf()
}

