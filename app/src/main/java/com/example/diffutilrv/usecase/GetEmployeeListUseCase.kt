package com.example.diffutilrv.usecase

import com.example.diffutilrv.enum.FilterType
import com.example.diffutilrv.model.Employee
import com.example.diffutilrv.repository.EmployeeRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class GetEmployeeListUseCase(private val employeeRepository: EmployeeRepository) {

    operator fun invoke(filterType: FilterType): Flow<List<Employee>> {
        return employeeRepository.getEmployeeList()
            .map {
                when (filterType) {
                    FilterType.Name -> getEmployeeByName(it)
                    FilterType.Role -> getEmployeeByRole(it)
                }
            }
    }

    private fun getEmployeeByName(employeeList: List<Employee>): List<Employee> {
        return employeeList.sortedBy { it.name }
    }

    private fun getEmployeeByRole(employeeList: List<Employee>): List<Employee> {
        return employeeList.sortedBy { it.role }
    }
}