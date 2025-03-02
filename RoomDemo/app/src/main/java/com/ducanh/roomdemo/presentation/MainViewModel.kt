package com.ducanh.roomdemo.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.ducanh.roomdemo.data.model.User
import com.ducanh.roomdemo.data.model.UserWithTasks
import com.ducanh.roomdemo.data.repository.UserRepositoryImpl

class MainViewModel(private val repository: UserRepositoryImpl) : ViewModel() {
    private val _users = MutableLiveData<List<User>>(listOf())
    val users: LiveData<List<User>> get() = _users

    private val _userWithTasks = MutableLiveData<List<UserWithTasks>>(listOf())
    val userWithTasks: LiveData<List<UserWithTasks>> get() = _userWithTasks

    fun addUser(user: User) {
        repository.insertUser(user)
        getUsersWithTasks()
    }

    fun getAllUsers() {
        repository.getAllUsers().also {
            _users.postValue(it)
        }
    }

    fun updateUser(user: User) {
        repository.updateUser(user)
        getUsersWithTasks()
    }

    fun deleteUser(user: User) {
        repository.deleteUser(user)
        getUsersWithTasks()
    }

    fun getUsersWithTasks() {
        repository.getUsersWithTasks().also {
            _userWithTasks.postValue(it)
        }
    }
}