package com.example.phase1mobile.model

import kotlinx.serialization.Contextual
import kotlinx.serialization.Serializable
import java.time.LocalDateTime
import java.util.Date
@Serializable
class Rating(
    val comment:String,
    @Contextual
    val doneAt:LocalDateTime,
    val doneBy:String,
    val rating:Int) {
}