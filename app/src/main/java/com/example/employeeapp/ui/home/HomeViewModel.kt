package com.example.employeeapp.ui.home

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.example.employeeapp.data.EmployeeDatabase
import com.example.employeeapp.data.EmployeesRepository

class HomeViewModel(application: Application) : AndroidViewModel(application) {
    private val employeeRepository: EmployeesRepository

    /**
     * Inisialisasi ViewModel dan membuat instance dari EmployeesRepository.
     */
    init {
        val employeeDao = EmployeeDatabase.getDatabase(application).employeeDao
        employeeRepository = EmployeesRepository(employeeDao)
    }

    suspend fun getAllEmployees() = employeeRepository.getAllEmployees()
}