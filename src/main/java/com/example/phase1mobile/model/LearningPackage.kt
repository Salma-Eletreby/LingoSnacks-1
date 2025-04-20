package com.example.phase1mobile.model

import kotlinx.serialization.Contextual
import kotlinx.serialization.Serializable
import java.time.LocalDate
import java.time.LocalDateTime
@Serializable
class LearningPackage (
    var packageId:Int,
    var author:String,
    var category:String,
    var description:String,
    var iconUrl:String ="",
    var keywords:Array<String> = emptyArray(),
    var language:String,
    var lastUpdatedDate: String,
    var level:String,
    var title:String,
    var version:Int,
    var words: MutableList<Word>,
    var ratings:MutableList<Rating> = mutableListOf()) {
}