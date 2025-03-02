package com.ducanh.roomdemo.data.dao

import androidx.paging.PagingSource
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

//    @Query("""
//        SELECT * FROM Task
//        WHERE userId = :userId
//        AND (:keyword IS NULL OR taskName LIKE '%' || :keyword)
//        ORDER BY id DESC
//    """)
//    fun getTasksByUserIdPaged(userId: Int, keyword: String?): PagingSource<Int, Task>

    @Query("SELECT * FROM Task WHERE userId = :userId ORDER BY id ASC LIMIT :limit OFFSET :offset")
    fun getTasksByUserIdPaged(userId: Int, limit: Int, offset: Int): List<Task>
}