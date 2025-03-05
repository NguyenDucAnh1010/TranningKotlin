package com.ducanh.dictionarydemo.presentation.repository

import com.ducanh.dictionarydemo.data.dao.WordDao
import com.ducanh.dictionarydemo.data.entity.Word

class DictionaryRepositoryImpl(private val wordDao: WordDao) :
    DictionaryRepository {
    override fun getAllWord(): List<Word> = wordDao.getAllWord()
    override fun getAllFavoriteWord(): List<Word> = wordDao.getAllFavoriteWord()
    override fun updateWord(word: Word){
        wordDao.updateWord(word)
    }
//    override fun getWords(length: Int): List<Word> = wordDao.getWords(length)
}