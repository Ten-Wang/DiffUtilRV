package com.example.diffutilrv.repository

import com.example.diffutilrv.api.ApiService
import com.example.diffutilrv.model.Employee
import kotlinx.coroutines.flow.Flow

class EmployeeRepository(
    private val api: ApiService
) {

    fun getEmployeeList(): Flow<List<Employee>> {
        return api.getEmployeeList()
    }
}