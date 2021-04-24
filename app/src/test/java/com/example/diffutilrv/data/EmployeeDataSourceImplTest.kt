package com.example.diffutilrv.data

import io.mockk.MockKAnnotations
import io.mockk.every
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Before
import org.junit.Test
import strikt.api.expectThat
import strikt.assertions.get
import strikt.assertions.hasSize
import strikt.assertions.isEqualTo

class EmployeeDataSourceImplTest {

    private lateinit var dataSource: EmployeeDataSource

    @MockK
    private lateinit var dummyEmployeeDataUtils: DummyEmployeeDataUtils

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
        dataSource = EmployeeDataSourceImpl(
            dummyEmployeeDataUtils = dummyEmployeeDataUtils,
            ioDispatcher = TestCoroutineDispatcher()
        )
    }

    @Test
    fun `getEmployees sort by name`() = runBlockingTest {
        every { dummyEmployeeDataUtils.getEmployeeListSortedByName() } returns listOf(
            Employee(1, "Employee 1", "Developer"),
            Employee(2, "Employee 2", "Tester"),
        )
        expectThat(dataSource.getEmployees(EmployeeSortBy.NAME)).hasSize(2).and {
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

    @Test
    fun `getEmployees sort by role`() = runBlockingTest {
        every { dummyEmployeeDataUtils.getEmployeeListSortedByRole() } returns listOf(
            Employee(2, "Employee 2", "Tester"),
            Employee(1, "Employee 1", "Developer"),
        )
        expectThat(dataSource.getEmployees(EmployeeSortBy.ROLE)).hasSize(2).and {
            get(0).and {
                get(Employee::id).isEqualTo(2)
                get(Employee::name).isEqualTo("Employee 2")
                get(Employee::role).isEqualTo("Tester")
            }
            get(1).and {
                get(Employee::id).isEqualTo(1)
                get(Employee::name).isEqualTo("Employee 1")
                get(Employee::role).isEqualTo("Developer")
            }
        }
    }
}