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

    override fun updateUser(user: User) {
        userDao.updateUser(user)
    }

    override fun deleteUser(user: User) {
        userDao.deleteUser(user)
    }
}