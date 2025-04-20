package com.example.phase1mobile.model

import kotlinx.serialization.Serializable

@Serializable
class Resource (
    var extension: String = "",
    var url:String,
    var title:String,
    var type:ResourceTypeEnum) {

}