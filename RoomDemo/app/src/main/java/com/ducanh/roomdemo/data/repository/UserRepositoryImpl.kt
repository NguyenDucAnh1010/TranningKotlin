package com.ducanh.roomdemo.data.repository

import com.ducanh.roomdemo.data.dao.TaskDao
import com.ducanh.roomdemo.data.dao.UserDao
import com.ducanh.roomdemo.data.model.Task
import com.ducanh.roomdemo.data.model.User
import com.ducanh.roomdemo.data.model.UserWithTasks

class UserRepositoryImpl(private val userDao: UserDao) : UserRepository {
    override fun insertUser(user: User) {
//        userDao.insert(user)
        userDao.insertUser(user)
    }

    override fun getAllUsers(): List<User> {
        return userDao.getAllUsers()
    }

    override fun updateUser(user: User) {
        userDao.updateUser(user)
    }

    override fun deleteUser(user: User) {
        userDao.deleteUser(user)
    }

    override fun getUsersWithTasks(): List<UserWithTasks> {
        return userDao.getUsersWithTasks()
    }
}