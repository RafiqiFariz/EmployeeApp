package com.example.employeeapp

import com.example.employeeapp.data.Employee
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

class EmployeesRepositoryTest {

    private lateinit var repository: FakeEmployeesRepository

    @Before
    fun setUp() {
        repository = FakeEmployeesRepository()
    }

    @Test
    fun `insert should add employee to repository`() = runTest {
        // Given
        val employeeId = 1
        val employee = Employee(
            id = employeeId,
            name = "John Doe",
            nip = "12345",
            gender = "Male",
            dateOfBirth = "01-01-1990",
            placeOfBirth = "Jakarta",
            address = "Jl. Example",
            photo = ""
        )

        // When
        repository.insert(employee)

        // Then
        val retrievedEmployee = repository.getEmployeeById(employeeId)
        assertEquals(employee, retrievedEmployee)
    }

    @Test
    fun `getEmployeeById should return correct employee`() = runTest {
        // Given
        val employeeId = 1
        val employee = Employee(
            id = employeeId,
            name = "Jane Smith",
            nip = "67890",
            gender = "Female",
            dateOfBirth = "01-01-1995",
            placeOfBirth = "Bandung",
            address = "Jl. Contoh",
            photo = ""
        )
        repository.insert(employee)

        // When
        val retrievedEmployee = repository.getEmployeeById(employeeId)

        // Then
        assertEquals(employee, retrievedEmployee)
    }

    @Test
    fun `getEmployeeById should return null for non-existent employee`() = runTest {
        // Given
        val nonExistentEmployeeId = 999

        // When
        val retrievedEmployee = repository.getEmployeeById(nonExistentEmployeeId)

        // Then
        assertEquals(null, retrievedEmployee)
    }
}

class FakeEmployeesRepository {
    private val employees = mutableMapOf<Int, Employee>()

    fun show(id: Int) = flowOf(employees[id])

    fun insert(employee: Employee) {
        employees[employee.id] = employee
    }

    fun update(employee: Employee) {
        employees[employee.id] = employee
    }

    fun getEmployeeById(id: Int): Employee? = employees[id]
}