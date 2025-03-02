package com.ducanh.roomdemo.data.repository

import com.ducanh.roomdemo.data.model.Task
import com.ducanh.roomdemo.data.model.User
import com.ducanh.roomdemo.data.model.UserWithTasks

interface UserRepository {
    fun insertUser(user: User)
    fun getUsersWithTasks(): List<UserWithTasks>
    fun getAllUsers(): List<User>
    fun updateUser(user: User)
    fun deleteUser(user: User)
}