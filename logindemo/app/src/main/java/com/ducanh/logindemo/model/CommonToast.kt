package com.ducanh.logindemo.model

import android.widget.Toast

class CommonToast (
    private val content: android.content.Context,
    private val text: String){

    fun showToast(){
        Toast.makeText(content,text,Toast.LENGTH_SHORT).show()
    }
}