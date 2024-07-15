package com.example.employeeapp.ui.profile

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.example.employeeapp.data.EmployeesRepository
import com.example.persiapanujikom.data.Employee
import com.example.persiapanujikom.data.EmployeeDatabase

class ProfileViewModel(application: Application) : AndroidViewModel(application) {
    private val employeeRepository: EmployeesRepository

    init {
        val employeeDao = EmployeeDatabase.getDatabase(application).employeeDao
        employeeRepository = EmployeesRepository(employeeDao)
    }

    fun show(id: Int) = employeeRepository.show(id)

    suspend fun insert(employee: Employee) = employeeRepository.insert(employee)
}