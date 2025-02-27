package com.ducanh.sleepstoriesdemo.data.repository

import com.ducanh.sleepstoriesdemo.data.model.ItemMenuFilter
import com.ducanh.sleepstoriesdemo.data.model.ItemMenuStory

interface HomeRepository {
    fun getMenuFilterList(): List<ItemMenuFilter>
    fun getMenuStoryList(): List<ItemMenuStory>
}