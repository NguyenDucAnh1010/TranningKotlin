package com.ducanh.dictionarydemo.data.dao

import androidx.room.Dao
import androidx.room.Query
import com.ducanh.dictionarydemo.data.entity.Word

@Dao
interface WordDao {

    @Query("SELECT * FROM word_tbl")
    fun getAllWord(): List<Word>

//    @Query("SELECT * FROM words WHERE isFavorite = true")
//    fun getAllFavoriteWord(): List<Word>

//    @Query("SELECT * FROM words LIMIT :length")
//    fun getWords(length: Int): List<Word>
}