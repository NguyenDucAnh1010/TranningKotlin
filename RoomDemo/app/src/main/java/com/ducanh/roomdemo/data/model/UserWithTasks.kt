package com.ducanh.roomdemo.data.model

import androidx.room.Embedded
import androidx.room.Relation

data class UserWithTasks(
    @Embedded val user: User,  // Dữ liệu User
    @Relation(
        parentColumn = "id",
        entityColumn = "userId"
    )
    val tasks: List<Task>
)