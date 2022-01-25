package com.arlib.arlibtest.common.data.db.models

import java.io.Serializable


data class Med (
    val medName:String,
    val dose:String,
    val strength:String,
    val className:String,
    val disease: String,
):Comparable<Med>,Serializable{
    override fun compareTo(other: Med): Int {
        return disease.compareTo(other.disease)
    }

}
