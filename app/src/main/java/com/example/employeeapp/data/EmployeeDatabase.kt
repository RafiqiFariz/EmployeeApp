package com.example.employeeapp.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

/**
 * Abstrak kelas yang merepresentasikan database Room untuk menyimpan data karyawan.
 */
@Database(entities = [Employee::class], version = 1, exportSchema = false)
abstract class EmployeeDatabase : RoomDatabase() {

    /**
     * Mendeklarasikan instance DAO untuk mengakses tabel `employees`.
     */
    abstract val employeeDao: EmployeeDao

    companion object {
        @Volatile
        private var INSTANCE: EmployeeDatabase? = null

        /**
         * Mendeklarasikan instance database. Jika instance belum ada, maka akan dibuat instance baru
         * dengan menggunakan synchronized untuk memastikan hanya satu instance yang dibuat.
         *
         * @param context Context yang diperlukan untuk membangun database.
         * @return Instance dari EmployeeDatabase.
         */
        fun getDatabase(context: Context): EmployeeDatabase {
            return INSTANCE ?: synchronized(this) {
                Room.databaseBuilder(
                    context.applicationContext,
                    EmployeeDatabase::class.java,
                    "employee_database"
                ).build().also { INSTANCE = it }
            }
        }
    }
}