package com.ducanh.roomdemo.presentation

import com.ducanh.roomdemo.data.model.Task
import com.ducanh.roomdemo.data.model.UserWithTasks

interface OnUserClickListener {
    fun onUserClick(userWithTasks: UserWithTasks)
    fun onDetailButtonClick(userWithTasks: UserWithTasks)
}