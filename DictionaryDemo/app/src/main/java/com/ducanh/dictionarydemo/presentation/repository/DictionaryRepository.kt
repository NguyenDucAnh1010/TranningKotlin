package com.ducanh.dictionarydemo.presentation.repository

import androidx.lifecycle.LiveData
import com.ducanh.dictionarydemo.data.entity.Word

interface DictionaryRepository {
    suspend fun getAllWord(index: Int): List<Word>
    suspend fun getAllFavoriteWord(index: Int): List<Word>
    suspend fun updateWord(word: Word)
}