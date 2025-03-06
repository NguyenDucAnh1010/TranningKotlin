package com.ducanh.dictionarydemo.ui.adapter

import com.ducanh.dictionarydemo.data.entity.Word

interface OnDictionaryClickListener {
    fun onWordClick(word: Word)
    fun onSoundClick(word: Word)
    fun onfavouriteClick(word: Word)
    fun onShareClick(word: Word)
}