package com.example.diffutilrv.repo

import com.example.diffutilrv.model.Employee
import com.example.diffutilrv.model.EmployeeListOrder

interface EmployeeDataRepository {
    suspend fun fetchEmployeeList(listOrder: EmployeeListOrder): List<Employee>
}