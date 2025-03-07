package com.ducanh.dictionarydemo.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.ducanh.dictionarydemo.data.entity.Word
import com.ducanh.dictionarydemo.presentation.repository.DictionaryRepository
import com.ducanh.dictionarydemo.presentation.repository.DictionaryRepositoryImpl
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class DictionaryViewModel(private val repository: DictionaryRepository) :
    ViewModel() {
    private val _words = MutableLiveData<List<Word>>(listOf())
    val words: LiveData<List<Word>> get() = _words

    private val _searchWords = MutableLiveData<List<Word>>(listOf())
    val searchWords: LiveData<List<Word>> get() = _searchWords

    fun getAllWord(index: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            val newWords = repository.getAllWord(index)
            val currentWords = _words.value.orEmpty()

            val mergedWords = (currentWords + newWords).distinctBy { it.word }

            _words.postValue(mergedWords)
        }
    }

    fun getAllFavoriteWord(index: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            val newWords = repository.getAllFavoriteWord(index)
            val currentWords = _words.value.orEmpty()

            val mergedWords = (currentWords + newWords).distinctBy { it.word }

            _words.postValue(mergedWords)
        }
    }

    fun updateAllWord(word: Word) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.updateWord(word)
            _words.postValue(_words.value?.map { if (it.word == word.word) word else it })
        }
    }


    fun updateFavouriteWord(word: Word) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.updateWord(word)
            _words.postValue(_words.value?.filterNot { it.word == word.word })
        }
    }

    fun searchWord(query: String) {
        viewModelScope.launch(Dispatchers.IO) {
            _searchWords.postValue(repository.searchWord(query))
        }
    }
}

class DictionaryViewModelFactory(
    private val repository: DictionaryRepository
) : ViewModelProvider.NewInstanceFactory() {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return DictionaryViewModel(repository) as T
    }
}