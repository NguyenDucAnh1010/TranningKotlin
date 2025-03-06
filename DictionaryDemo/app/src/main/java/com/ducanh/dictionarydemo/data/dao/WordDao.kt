package com.ducanh.dictionarydemo.data.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Query
import androidx.room.Update
import com.ducanh.dictionarydemo.data.entity.Word
import com.ducanh.dictionarydemo.utils.Constants.DISPLAY_LIST

@Dao
interface WordDao {

    @Query("SELECT * FROM word_tbl LIMIT $DISPLAY_LIST OFFSET :index")
    suspend fun getAllWord(index: Int): List<Word>

    @Query("SELECT * FROM word_tbl WHERE isFavorite = true LIMIT $DISPLAY_LIST OFFSET :index")
    suspend fun getAllFavoriteWord(index: Int): List<Word>

    @Update
    suspend fun updateWord(word: Word)

    @Query("SELECT * FROM word_tbl WHERE word LIKE '%' || :query || '%'")
    suspend fun searchWord(query: String): List<Word>
}