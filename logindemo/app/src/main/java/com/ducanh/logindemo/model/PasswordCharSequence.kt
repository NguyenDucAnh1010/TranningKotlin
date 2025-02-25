package com.ducanh.logindemo.model

class PasswordCharSequence(private val source: CharSequence) : CharSequence {
    override val length: Int
        get() = source.length

    override fun get(index: Int): Char {
        return '*'
    }

    override fun subSequence(startIndex: Int, endIndex: Int): CharSequence {
        return source.subSequence(startIndex, endIndex)
    }
}