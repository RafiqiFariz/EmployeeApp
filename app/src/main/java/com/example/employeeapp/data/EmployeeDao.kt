package com.example.employeeapp.data

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

/**
 * Data Access Object (DAO) untuk mengakses tabel `employees` dalam database.
 */
@Dao
interface EmployeeDao {
    @Query("SELECT * FROM employees")
    fun getAllEmployees(): Flow<List<Employee>>

    /**
     * Memperbarui data karyawan dalam database.
     *
     * @param employee Objek `Employee` yang akan diperbarui.
     */
    @Update
    suspend fun update(employee: Employee)

    /**
     * Menyisipkan data karyawan baru ke dalam database.
     *
     * @param employee Objek `Employee` yang akan disisipkan.
     */
    @Insert
    suspend fun insert(employee: Employee)

    /**
     * Menghapus data karyawan dari database.
     */
    @Delete
    suspend fun delete(id: Int)

    /**
     * Mengambil data karyawan berdasarkan ID.
     *
     * @param id ID karyawan yang ingin diambil.
     * @return `Flow<Employee?>` yang berisi data karyawan dengan ID yang diberikan,
     * atau null jika tidak ditemukan.
     */
    @Query("SELECT * FROM employees WHERE id = :id")
    fun getEmployeeById(id: Int): Flow<Employee?>
}