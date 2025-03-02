package com.ducanh.roomdemo.data.repository

import androidx.paging.PagingData
import androidx.paging.PagingSource
import com.ducanh.roomdemo.data.model.Task
import kotlinx.coroutines.flow.Flow

interface TaskRepository {
    fun insertTask(task: Task)
    fun getTasksByUserId(userId: Int): List<Task>
//    fun getTasksByUserIdPaged(userId: Int, keyword: String?): Flow<PagingData<Task>>
}