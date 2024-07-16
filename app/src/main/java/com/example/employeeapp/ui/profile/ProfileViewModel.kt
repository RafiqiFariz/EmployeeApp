package com.example.employeeapp.ui.profile

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.example.employeeapp.data.EmployeesRepository
import com.example.employeeapp.data.Employee
import com.example.employeeapp.data.EmployeeDatabase

/**
 * ViewModel untuk mengelola data karyawan dan berinteraksi dengan repository
 *
 * @param application Aplikasi yang terkait dengan ViewModel ini.
 * @property employeeRepository Repositori karyawan untuk mengelola operasi database.
 */
class ProfileViewModel(application: Application) : AndroidViewModel(application) {
    private val employeeRepository: EmployeesRepository

    /**
     * Inisialisasi ViewModel dan membuat instance dari EmployeesRepository.
     */
    init {
        val employeeDao = EmployeeDatabase.getDatabase(application).employeeDao
        employeeRepository = EmployeesRepository(employeeDao)
    }

    /**
     * Menampilkan detail karyawan berdasarkan ID.
     *
     * @param id ID karyawan yang akan ditampilkan.
     * @return Data karyawan yang sesuai dengan ID yang diberikan.
     */
    fun show(id: Int) = employeeRepository.show(id)

    /**
     * Memasukkan data karyawan baru ke dalam database.
     *
     * @param employee Data karyawan yang akan disisipkan.
     */
    suspend fun insert(employee: Employee) = employeeRepository.insert(employee)
}