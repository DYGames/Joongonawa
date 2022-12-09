package com.team7.joongonawa
import java.io.Serializable

data class CategoryTypeData (
    var pic : String,
    val name : String,
    val category : Int,
    val id : Int,
) : Serializable