package com.example.diffutilrv.data

import javax.inject.Inject

class EmployeeRepository @Inject constructor() {

    fun getEmployees() = FakeEmployeeDatabase.employeeList
}
