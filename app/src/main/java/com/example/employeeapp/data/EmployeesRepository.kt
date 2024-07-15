package com.example.employeeapp.data

import com.example.persiapanujikom.data.Employee
import com.example.persiapanujikom.data.EmployeeDao
import kotlinx.coroutines.flow.Flow

class EmployeesRepository(val dao: EmployeeDao) {
    fun show(id: Int): Flow<Employee?> = dao.getEmployeeById(id)

    suspend fun insert(employee: Employee) = dao.insert(employee)

    suspend fun update(employee: Employee) = dao.update(employee)
}