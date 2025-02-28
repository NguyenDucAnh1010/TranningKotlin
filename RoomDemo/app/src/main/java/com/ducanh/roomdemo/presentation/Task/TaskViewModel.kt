package com.ducanh.roomdemo.presentation.Task

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.ducanh.roomdemo.data.model.Task
import com.ducanh.roomdemo.data.model.User
import com.ducanh.roomdemo.data.model.UserWithTasks
import com.ducanh.roomdemo.data.repository.UserRepositoryImpl

class TaskViewModel(private val repository: UserRepositoryImpl) : ViewModel() {
    private val _userWithTasks = MutableLiveData<List<UserWithTasks>>(listOf())
    val userWithTasks: LiveData<List<UserWithTasks>> get() = _userWithTasks

    fun getUsersWithTasks(){
        repository.getUsersWithTasks().also {
            _userWithTasks.postValue(it)
        }
    }

    fun addTask(task: Task){
        repository.insertTask(task)
        getUsersWithTasks()
    }
}