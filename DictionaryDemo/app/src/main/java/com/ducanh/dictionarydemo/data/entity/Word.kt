package com.ducanh.dictionarydemo.data.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable

@Entity(tableName = "words")
data class Word(
    @PrimaryKey
    val word: String,
    val av: ByteArray?,
    val dnpn: ByteArray?,
    val aa: ByteArray?,
    val mean: String?,
    var isFavorite: Boolean = false
) : Serializable