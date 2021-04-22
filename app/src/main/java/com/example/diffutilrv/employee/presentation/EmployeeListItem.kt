package com.example.diffutilrv.employee.presentation

import com.example.diffutilrv.R
import com.example.diffutilrv.employee.data.Employee
import com.example.recyclerview.ListItem

data class EmployeeListItem(
    val id: Int,
    val name: String,
    val role: String,
    override val layoutId: Int = R.layout.list_item,
) : ListItem

fun Employee.toListItem(): EmployeeListItem {
    return EmployeeListItem(
        id = this.id,
        name = "Employee $id",
        role = this.role,
    )
}
