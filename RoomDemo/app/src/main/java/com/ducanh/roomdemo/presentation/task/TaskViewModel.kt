package com.ducanh.roomdemo.presentation.task

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.cachedIn
import com.ducanh.roomdemo.data.dao.TaskDao
import com.ducanh.roomdemo.data.model.Task
import com.ducanh.roomdemo.data.repository.TaskPagingSource

//class TaskViewModel(private val repository: TaskRepositoryImpl) : ViewModel() {
//    private val _tasks = MutableLiveData<List<Task>>(listOf())
//    val tasks: LiveData<List<Task>> get() = _tasks
//
//    fun getTasksByUserId(userId: Int) {
//        repository.getTasksByUserId(userId).also {
//            _tasks.postValue(it)
//        }
//    }
//
//    fun addTask(task: Task, userId: Int) {
//        repository.insertTask(task)
//        getTasksByUserId(userId)
//    }
//}

class TaskViewModel(private val userId: Int, private val dao: TaskDao) : ViewModel() {
    fun addTask(task: Task) {
        dao.insertTask(task)
    }

    val data = Pager(
        PagingConfig(
            pageSize = 20,
            enablePlaceholders = false,
            initialLoadSize = 20
        ),
    ) {
        TaskPagingSource(userId, dao)
    }.flow.cachedIn(viewModelScope)
}

class TaskViewModelFactory(
    private val userId: Int,
    private val dao: TaskDao
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(TaskViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return TaskViewModel(userId, dao) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}