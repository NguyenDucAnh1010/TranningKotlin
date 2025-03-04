package com.ducanh.dictionarydemo.data

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import java.io.File
import java.io.FileOutputStream

class DatabaseHelper(context: Context) : SQLiteOpenHelper(context, DB_NAME, null, DB_VERSION) {

    companion object {
        private const val DB_NAME = "my_database.db" // Tên database
        private const val DB_VERSION = 1 // Phiên bản database
        private const val DB_PATH = "/data/data/com.yourpackage.name/databases/"
    }

    private val mContext: Context = context

    override fun onCreate(db: SQLiteDatabase?) {
        // Không tạo database mới vì chúng ta dùng database có sẵn
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        // Xử lý nâng cấp database nếu cần
    }

    fun copyDatabase() {
        val dbFile = File(DB_PATH + DB_NAME)

//        if (!dbFile.exists()) { // Chỉ copy nếu chưa có database
//            this.readableDatabase // Tạo thư mục databases nếu chưa có
//            mContext.assets.open(DB_NAME).use { inputStream ->
//                FileOutputStream(DB_PATH + DB_NAME).use { outputStream ->
//                    inputStream.copyTo(outputStream)
//                }
//            }
//        }

        this.readableDatabase // Tạo thư mục databases nếu chưa có
        mContext.assets.open(DB_NAME).use { inputStream ->
            FileOutputStream(DB_PATH + DB_NAME).use { outputStream ->
                inputStream.copyTo(outputStream)
            }
        }
    }
}
