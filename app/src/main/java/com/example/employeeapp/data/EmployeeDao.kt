package com.example.persiapanujikom.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

@Dao
interface EmployeeDao {
    @Update
    suspend fun update(employee: Employee)

    @Insert
    suspend fun insert(employee: Employee)

    @Query("SELECT * FROM employees WHERE id = :id")
    fun getEmployeeById(id: Int): Flow<Employee?>
}