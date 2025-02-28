package com.ducanh.roomdemo.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.ducanh.roomdemo.data.model.Task

@Dao
interface TaskDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertTask(task: Task)

    @Query("SELECT * FROM Task WHERE userId = :userId")
    fun getTasksByUserId(userId: Int): List<Task>
}