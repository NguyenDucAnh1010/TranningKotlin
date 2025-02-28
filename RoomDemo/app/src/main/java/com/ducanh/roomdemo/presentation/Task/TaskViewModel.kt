package com.ducanh.roomdemo.presentation.Task

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.ducanh.roomdemo.data.model.Task
import com.ducanh.roomdemo.data.model.User
import com.ducanh.roomdemo.data.model.UserWithTasks
import com.ducanh.roomdemo.data.repository.UserRepositoryImpl

class TaskViewModel(private val repository: UserRepositoryImpl) : ViewModel() {
    private val _tasks = MutableLiveData<List<Task>>(listOf())
    val tasks: LiveData<List<Task>> get() = _tasks

    fun getTasksByUserId(userId:Int){
        repository.getTasksByUserId(userId).also {
            _tasks.postValue(it)
        }
    }

    fun addTask(task: Task,userId: Int){
        repository.insertTask(task)
        getTasksByUserId(userId)
    }
}