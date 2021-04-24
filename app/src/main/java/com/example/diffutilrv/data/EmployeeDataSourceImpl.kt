package com.example.diffutilrv.data

import com.example.diffutilrv.async.CoroutineDispatcherQualifier
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import javax.inject.Inject

class EmployeeDataSourceImpl @Inject constructor(
    private val dummyEmployeeDataUtils: DummyEmployeeDataUtils,
    @CoroutineDispatcherQualifier.Io private val ioDispatcher: CoroutineDispatcher,
) : EmployeeDataSource {
    override suspend fun getEmployees(sortBy: EmployeeSortBy): List<Employee> = withContext(ioDispatcher) {
        when (sortBy) {
            EmployeeSortBy.ROLE -> dummyEmployeeDataUtils.getEmployeeListSortedByRole()
            EmployeeSortBy.NAME -> dummyEmployeeDataUtils.getEmployeeListSortedByName()
        }
    }
}