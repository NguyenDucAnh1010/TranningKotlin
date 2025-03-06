package com.ducanh.dictionarydemo.presentation.repository

import com.ducanh.dictionarydemo.data.dao.WordDao
import com.ducanh.dictionarydemo.data.entity.Word

class DictionaryRepositoryImpl(private val wordDao: WordDao) :
    DictionaryRepository {
    override suspend fun getAllWord(index: Int): List<Word> = wordDao.getAllWord(index)
    override suspend fun getAllFavoriteWord(index: Int): List<Word> = wordDao.getAllFavoriteWord(index)
    override suspend fun updateWord(word: Word){
        wordDao.updateWord(word)
    }
}