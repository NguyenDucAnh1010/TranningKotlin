package com.example.androidtraining2.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.ducanh.dictionarydemo.data.dao.WordDao
import com.ducanh.dictionarydemo.data.entity.Word

@Database(entities = [Word::class], version = 7)
abstract class DictionaryDatabase : RoomDatabase() {
    abstract fun wordDao(): WordDao

    companion object {
        private var INSTANCE: DictionaryDatabase? = null

        fun getDatabase(context: Context): DictionaryDatabase? {
            if (INSTANCE == null) {
                synchronized(DictionaryDatabase::class) {
                    INSTANCE = Room.databaseBuilder(
                        context.applicationContext,
                        DictionaryDatabase::class.java, "room.db"
                    ).allowMainThreadQueries()
                        .createFromAsset("dictionary_db.db")
                        .fallbackToDestructiveMigration()
                        .build()
                }
            }
            return INSTANCE
        }

        fun destroyInstance() {
            INSTANCE = null
        }
    }
}