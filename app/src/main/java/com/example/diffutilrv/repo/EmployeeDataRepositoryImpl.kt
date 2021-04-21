package com.example.diffutilrv.repo

import com.example.diffutilrv.model.Employee
import com.example.diffutilrv.model.EmployeeListOrder
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.withContext

class EmployeeDataRepositoryImpl(
    private val workerDispatcher: CoroutineDispatcher = Dispatchers.IO
) : EmployeeDataRepository {
    private val dummyEmployeeDataUtils = DummyEmployeeDataUtils()

    override suspend fun fetchEmployeeList(listOrder: EmployeeListOrder): List<Employee> =
        withContext(workerDispatcher) {
            delay(500) // fake network latency; cooperative cancellation
            when (listOrder) {
                EmployeeListOrder.SORT_BY_NAME -> {
                    dummyEmployeeDataUtils.employeeListSortedByName
                }
                EmployeeListOrder.SORT_BY_ROLE -> {
                    dummyEmployeeDataUtils.employeeListSortedByRole
                }
            }
        }
}