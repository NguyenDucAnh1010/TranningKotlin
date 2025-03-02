package com.ducanh.roomdemo.data.repository

import com.ducanh.roomdemo.data.model.Task

interface TaskRepository {
    fun insertTask(task: Task)
    fun getTasksByUserId(userId: Int): List<Task>
}