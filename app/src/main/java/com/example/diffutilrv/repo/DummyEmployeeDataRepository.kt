package com.example.diffutilrv.repo

import com.example.diffutilrv.model.Employee
import com.example.diffutilrv.model.EmployeeListOrder
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.withContext

class DummyEmployeeDataRepository(
    private val workerDispatcher: CoroutineDispatcher = Dispatchers.IO
) : EmployeeDataRepository {

    override suspend fun fetchEmployeeList(listOrder: EmployeeListOrder): List<Employee> =
        withContext(workerDispatcher) {
            delay(500) // fake network latency; cooperative cancellation
            createEmployeeList().apply { sortBy(listOrder.getSelector()) }
        }

    private fun EmployeeListOrder.getSelector(): (Employee) -> String {
        return when (this) {
            EmployeeListOrder.SORT_BY_NAME -> {
                { it.name }
            }
            EmployeeListOrder.SORT_BY_ROLE -> {
                { it.role }
            }
        }
    }

    private fun createEmployeeList(): MutableList<Employee> {
        return mutableListOf(
            Employee(1, "Employee 1", "Developer"),
            Employee(2, "Employee 2", "Tester"),
            Employee(3, "Employee 3", "Support"),
            Employee(4, "Employee 4", "Sales Manager"),
            Employee(5, "Employee 5", "Manager"),
            Employee(6, "Employee 6", "Team lead"),
            Employee(7, "Employee 7", "Scrum Master"),
            Employee(8, "Employee 8", "Sr. Tester"),
            Employee(9, "Employee 9", "Sr. Developer")
        )
    }
}