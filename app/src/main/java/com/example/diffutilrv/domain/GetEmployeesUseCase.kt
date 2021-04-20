package com.example.diffutilrv.domain

import com.example.diffutilrv.data.Employee
import com.example.diffutilrv.data.EmployeeRepository

class GetEmployeesUseCase(
    private val employeeRepository: EmployeeRepository
) {
    operator fun invoke(filterStrategy: FilterStrategy): List<Employee> {
        return filterEmployees(employeeRepository.getEmployees(), filterStrategy)
    }

    private fun filterEmployees(employees: List<Employee>, filterStrategy: FilterStrategy): List<Employee> {
        return when (filterStrategy) {
            FilterStrategy.ByName -> filterByName(employees)
            FilterStrategy.ByRole -> filterByRole(employees)
        }
    }

    private fun filterByName(employees: List<Employee>): List<Employee> {
        return employees.sortedWith { e1: Employee, e2: Employee ->
            e1.name.compareTo(e2.name)
        }
    }

    private fun filterByRole(employees: List<Employee>): List<Employee> {
        return employees.sortedWith { e1: Employee, e2: Employee ->
            e2.role.compareTo(e1.role)
        }
    }
}

enum class FilterStrategy {
    ByName,
    ByRole
}
