package com.example.diffutilrv.employee.presentation

import com.example.diffutilrv.R
import com.example.diffutilrv.employee.data.Employee
import com.example.recyclerview.ListItem

data class EmployeeListItem(
    val id: Int,
    val name: String,
    val role: String,
    private val showName: (String) -> Unit,
    private val showRole: (String) -> Unit,
    override val layoutId: Int = R.layout.list_item,
) : ListItem {

    fun showNameDetail() {
        showName(name)
    }

    fun showRoleDetail() {
        showRole(role)
    }
}

fun Employee.toListItem(
    showName: (String) -> Unit,
    showRole: (String) -> Unit
): EmployeeListItem {
    return EmployeeListItem(
        id = this.id,
        name = "Employee $id",
        role = this.role,
        showName = showName,
        showRole = showRole
    )
}
