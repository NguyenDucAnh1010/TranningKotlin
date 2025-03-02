package com.ducanh.roomdemo.data.repository

import com.ducanh.roomdemo.data.dao.TaskDao
import com.ducanh.roomdemo.data.model.Task

class TaskRepositoryImpl(private val taskDao: TaskDao) : TaskRepository {
    override fun insertTask(task: Task) {
        taskDao.insertTask(task)
    }

    override fun getTasksByUserId(userId: Int): List<Task> {
        return taskDao.getTasksByUserId(userId)
    }
}