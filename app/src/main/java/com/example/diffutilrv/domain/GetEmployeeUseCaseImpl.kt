package com.example.diffutilrv.domain

import com.example.diffutilrv.data.Employee
import com.example.diffutilrv.data.EmployeeDataSource
import com.example.diffutilrv.data.EmployeeSortBy
import javax.inject.Inject

class GetEmployeeUseCaseImpl @Inject constructor(
    private val employeeDataSource: EmployeeDataSource,
) : GetEmployeeUseCase {
    override suspend fun invoke(sortBy: EmployeeSortBy): List<Employee> = employeeDataSource.getEmployees(sortBy)
}