package com.example.diffutilrv.uimodel

import com.example.diffutilrv.model.Employee

data class EmployeeCheckbox(
    val employee: Employee,
    val isChecked: Boolean
) {
    companion object {
        fun newInstance(employee: Employee, selectedIdSet: Set<Int>): EmployeeCheckbox {
            return EmployeeCheckbox(employee, selectedIdSet.contains(employee.id))
        }
    }

    val id = employee.id
    val name = employee.name
    val role = employee.role
    val cost = employee.cost
}