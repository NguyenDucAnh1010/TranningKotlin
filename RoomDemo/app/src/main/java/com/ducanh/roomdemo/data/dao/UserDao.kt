package com.ducanh.roomdemo.data.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.ducanh.roomdemo.data.model.User

@Dao
interface UserDao {
    @Insert
    fun insert(user: User)

    @Query("SELECT * FROM user")
    fun getAllUsers(): List<User>

    @Update
    fun updateUser(vararg users: User)

    @Delete
    fun deleteUser(user: User)
}