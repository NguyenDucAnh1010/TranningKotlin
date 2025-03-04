package com.ducanh.dictionarydemo.presentation.repository

import com.ducanh.dictionarydemo.data.entity.Word

interface DictionaryRepository {
    suspend fun getAllWord(): List<Word>
    suspend fun getAllFavoriteWord(): List<Word>
    suspend fun getWords(length: Int): List<Word>
}