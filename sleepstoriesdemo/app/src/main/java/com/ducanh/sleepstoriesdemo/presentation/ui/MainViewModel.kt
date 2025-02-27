package com.ducanh.sleepstoriesdemo.presentation.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.ducanh.sleepstoriesdemo.data.model.ItemMenuFilter
import com.ducanh.sleepstoriesdemo.data.model.ItemMenuStory
import com.ducanh.sleepstoriesdemo.data.repository.HomeRepository
import com.ducanh.sleepstoriesdemo.data.repository.HomeRepositoryImpl

class MainViewModel : ViewModel() {
    private val repository: HomeRepository = HomeRepositoryImpl()

    private val _menuFilters = MutableLiveData<List<ItemMenuFilter>>(listOf())
    val menuFilters: LiveData<List<ItemMenuFilter>> get() = _menuFilters

    private val _menuStories = MutableLiveData<List<ItemMenuStory>>(listOf())
    val menuStories: LiveData<List<ItemMenuStory>> get() = _menuStories

    fun getMenuFilterData() {
        repository.getMenuFilterList().also {
            _menuFilters.postValue(it)
        }
    }

    fun getMenuStoryData() {
        repository.getMenuStoryList().also {
            _menuStories.postValue(it)
        }
    }
}