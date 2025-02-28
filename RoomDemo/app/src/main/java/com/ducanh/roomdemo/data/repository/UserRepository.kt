package com.ducanh.roomdemo.data.repository

import com.ducanh.roomdemo.data.model.Task
import com.ducanh.roomdemo.data.model.User
import com.ducanh.roomdemo.data.model.UserWithTasks

interface UserRepository {
    fun insertUser(user: User)
    fun insertTask(task: Task)
    fun getUsersWithTasks(): List<UserWithTasks>
    fun getAllUsers(): List<User>
    fun getTasksByUserId(userId: Int): List<Task>
    fun updateUser(user: User)
    fun deleteUser(user: User)
}