package com.example.diffutilrv.domain

import com.example.diffutilrv.data.Employee
import com.example.diffutilrv.data.EmployeeDataSource
import com.example.diffutilrv.data.EmployeeSortBy
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Before
import org.junit.Test
import strikt.api.expectThat
import strikt.assertions.get
import strikt.assertions.hasSize
import strikt.assertions.isEqualTo

class GetEmployeeUseCaseImplTest {

    private lateinit var useCase: GetEmployeeUseCase

    @MockK
    private lateinit var employeeDataSource: EmployeeDataSource

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
        useCase = GetEmployeeUseCaseImpl(employeeDataSource)
    }

    @Test
    operator fun invoke() = runBlockingTest {
        coEvery { employeeDataSource.getEmployees(EmployeeSortBy.NAME) } returns listOf(
            Employee(1, "Employee 1", "Developer"),
            Employee(2, "Employee 2", "Tester"),
        )
        expectThat(useCase(EmployeeSortBy.NAME)).hasSize(2).and {
            get(0).and {
                get(Employee::id).isEqualTo(1)
                get(Employee::name).isEqualTo("Employee 1")
                get(Employee::role).isEqualTo("Developer")
            }
            get(1).and {
                get(Employee::id).isEqualTo(2)
                get(Employee::name).isEqualTo("Employee 2")
                get(Employee::role).isEqualTo("Tester")
            }
        }
    }
}