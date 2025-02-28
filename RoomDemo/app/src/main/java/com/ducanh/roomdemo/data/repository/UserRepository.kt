package com.ducanh.roomdemo.data.repository

import com.ducanh.roomdemo.data.model.User

interface UserRepository {
    fun insertUser(user: User)
    fun getAllUsers(): List<User>
}