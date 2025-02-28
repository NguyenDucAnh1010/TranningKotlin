package com.ducanh.roomdemo.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ducanh.roomdemo.data.model.User
import com.ducanh.roomdemo.data.repository.UserRepositoryImpl
import kotlinx.coroutines.launch

class MainViewModel(private val repository: UserRepositoryImpl) : ViewModel() {
    private val _users = MutableLiveData<List<User>>(listOf())
    val users: LiveData<List<User>> get() = _users

    fun insert(user: User) {
        repository.insertUser(user)
        getAllUsers()
    }

    fun getAllUsers() {
        _users.postValue(repository.getAllUsers())
    }
}