package com.example.diffutilrv.data

interface EmployeeDataSource {
    suspend fun getEmployees(sortBy: EmployeeSortBy): List<Employee>
}