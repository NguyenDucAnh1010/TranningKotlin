package com.ducanh.dictionarydemo.data.dao

import androidx.room.Dao
import androidx.room.Query
import com.ducanh.dictionarydemo.data.entity.Word

@Dao
interface WordDao {

    @Query("SELECT * FROM words")
    suspend fun getAllWord(): List<Word>

    @Query("SELECT * FROM words WHERE isFavorite = true")
    suspend fun getAllFavoriteWord(): List<Word>

    @Query("SELECT * FROM words LIMIT :length")
    suspend fun getWords(length: Int): List<Word>
}