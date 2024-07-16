package com.example.employeeapp.data

import androidx.room.Delete
import kotlinx.coroutines.flow.Flow

/**
 * Repository untuk mengelola operasi database yang terkait dengan entitas `Employee`.
 *
 * @param dao Data Access Object (DAO) yang digunakan untuk mengakses tabel `employees`.
 */
open class EmployeesRepository(private val dao: EmployeeDao) {
    /**
     * Mengambil semua data karyawan dari database.
     *
     * @return `Flow<List<Employee>>` yang berisi daftar `Employee`.
     */
    open val employees: Flow<List<Employee>> = dao.getAllEmployees()

    open suspend fun getAllEmployees(): Flow<List<Employee>> = dao.getAllEmployees()

    /**
     * Menghapus data karyawan dari database berdasarkan ID.
     *
     * @param id ID karyawan yang akan dihapus.
     */
    @Delete
    open suspend fun delete(id: Int) = dao.delete(id)

    /**
     * Mengambil data karyawan berdasarkan ID.
     *
     * @param id ID karyawan yang ingin diambil.
     * @return `Flow<Employee?>` yang berisi data karyawan dengan ID yang diberikan, atau null jika tidak ditemukan.
     */
    open fun show(id: Int): Flow<Employee?> = dao.getEmployeeById(id)

    /**
     * Menyisipkan data karyawan baru ke dalam database.
     *
     * @param employee Objek `Employee` yang akan disisipkan.
     */
    open suspend fun insert(employee: Employee) = dao.insert(employee)

    /**
     * Memperbarui data karyawan dalam database.
     *
     * @param employee Objek `Employee` yang akan diperbarui.
     */
    open suspend fun update(employee: Employee) = dao.update(employee)
}
