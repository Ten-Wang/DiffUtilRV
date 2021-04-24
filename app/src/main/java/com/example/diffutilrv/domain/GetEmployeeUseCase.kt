package com.example.diffutilrv.domain

import com.example.diffutilrv.data.Employee
import com.example.diffutilrv.data.EmployeeSortBy

interface GetEmployeeUseCase {
    suspend operator fun invoke(sortBy: EmployeeSortBy): List<Employee>
}