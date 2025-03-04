package com.ducanh.dictionarydemo.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.ducanh.dictionarydemo.data.entity.Word
import com.ducanh.dictionarydemo.presentation.repository.DictionaryRepositoryImpl
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class DictionaryViewModel(private val repository: DictionaryRepositoryImpl) :
    ViewModel() {
    private val _words = MutableLiveData<List<Word>>(listOf())
    val words: LiveData<List<Word>> get() = _words

    fun getAllWord() {
        viewModelScope.launch(Dispatchers.IO) {
            _words.postValue(repository.getAllWord())
        }
    }

    fun getAllFavoriteWord() {
        viewModelScope.launch(Dispatchers.IO) {
//            _words.postValue(repository.getAllFavoriteWord())
        }
    }

//    fun getWords(length: Int) {
//        viewModelScope.launch(Dispatchers.IO) {
//            _words.postValue(repository.getWords(length))
//        }
//    }
}

class DictionaryViewModelFactory(
    private val repository: DictionaryRepositoryImpl
) : ViewModelProvider.NewInstanceFactory() {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return DictionaryViewModel(repository) as T
    }
}