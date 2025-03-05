package com.ducanh.dictionarydemo.presentation.repository

import com.ducanh.dictionarydemo.data.entity.Word

interface DictionaryRepository {
    fun getAllWord(): List<Word>
    fun getAllFavoriteWord(): List<Word>
    fun updateWord(word: Word)
//    fun getWords(length: Int): List<Word>
}