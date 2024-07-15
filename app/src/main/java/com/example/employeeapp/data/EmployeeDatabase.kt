package com.example.persiapanujikom.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [Employee::class], version = 1, exportSchema = false)
abstract class EmployeeDatabase : RoomDatabase() {
    abstract val employeeDao: EmployeeDao

    companion object {
        @Volatile
        private var INSTANCE: EmployeeDatabase? = null

        fun getDatabase(context: Context): EmployeeDatabase {
            return INSTANCE ?: synchronized(this) {
                Room.databaseBuilder(context, EmployeeDatabase::class.java, "employee_database")
                    .build()
                    .also { INSTANCE = it }
            }
        }
    }
}