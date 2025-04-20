package com.example.phase1mobile.model

import android.content.Context
import com.example.phase1mobile.repository.PackageRepo
import kotlinx.serialization.Serializable

@Serializable
class Word (
    var text:String
){
    var definitions:MutableList<Definition> = mutableListOf()
    var sentences:MutableList<Sentence> = mutableListOf()
    var resources:MutableList<Resource> = mutableListOf()

    fun addDefinition( definition: Definition ){
        definitions.add(definition)
    }
    fun addSentence( sentence: Sentence ){
        sentences.add(sentence)
    }
    fun addResource( resource: Resource ){
        resources.add(resource)
    }


}
