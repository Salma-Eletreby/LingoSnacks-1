package com.example.phase1mobile.model

import kotlinx.serialization.Serializable

@Serializable
class Sentence (
    var text:String,
    var resources:MutableList<Resource> = mutableListOf()
) {

}