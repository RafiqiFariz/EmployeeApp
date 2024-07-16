package com.example.employeeapp.ui.profile.edit

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.example.employeeapp.data.EmployeesRepository
import com.example.employeeapp.data.Employee
import com.example.employeeapp.data.EmployeeDatabase

/**
 * ViewModel untuk mengelola data karyawan dan berinteraksi dengan repository.
 *
 * @param application Aplikasi yang terkait dengan ViewModel ini.
 */
class EditProfileViewModel(application: Application) : AndroidViewModel(application) {
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
     * @return LiveData<Employee> yang berisi data karyawan yang sesuai dengan ID yang diberikan.
     */
    fun show(id: Int) = employeeRepository.show(id)

    /**
     * Memperbarui data karyawan di database.
     *
     * @param employee Data karyawan yang akan diperbarui.
     */
    suspend fun update(employee: Employee) = employeeRepository.update(employee)
}