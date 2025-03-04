package com.ducanh.dictionarydemo.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ducanh.dictionarydemo.data.entity.Word
import com.ducanh.dictionarydemo.presentation.repository.DictionaryRepositoryImpl
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DictionaryViewModel @Inject constructor(private val repository: DictionaryRepositoryImpl) :
    ViewModel() {
    private val _words = MutableLiveData<List<Word>>(listOf())
    val words: LiveData<List<Word>> get() = _words

    fun getAllWord() {
        viewModelScope.launch {
            _words.postValue(repository.getAllWord())
        }
    }

    fun getAllFavoriteWord() {
        viewModelScope.launch {
            _words.postValue(repository.getAllFavoriteWord())
        }
    }

    fun getWords(length: Int) {
        viewModelScope.launch {
            _words.postValue(repository.getWords(length))
        }
    }
}