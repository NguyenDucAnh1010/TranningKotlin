package com.ducanh.roomdemo.data.repository

import com.ducanh.roomdemo.data.dao.UserDao
import com.ducanh.roomdemo.data.model.User

class UserRepositoryImpl(private val userDao: UserDao) : UserRepository {
    override fun insertUser(user: User) {
        userDao.insert(user)
    }

    override fun getAllUsers(): List<User> {
        return userDao.getAllUsers()
    }
}