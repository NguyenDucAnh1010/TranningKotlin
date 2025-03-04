package com.ducanh.dictionarydemo.presentation.repository

import com.ducanh.dictionarydemo.data.dao.WordDao
import com.ducanh.dictionarydemo.data.entity.Word

class DictionaryRepositoryImpl(private val wordDao: WordDao) : DictionaryRepository {
    override suspend fun getAllWord(): List<Word> = wordDao.getAllWord()
    override suspend fun getAllFavoriteWord(): List<Word> = wordDao.getAllFavoriteWord()
    override suspend fun getWords(length: Int): List<Word> = wordDao.getWords(length)
}