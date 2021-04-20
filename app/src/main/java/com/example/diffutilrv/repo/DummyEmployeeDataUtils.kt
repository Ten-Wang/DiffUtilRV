package com.example.diffutilrv.repo

import com.example.diffutilrv.model.Employee

object DummyEmployeeDataUtils {
    val employeeListSortedByName: List<Employee>
        get() {
            return createEmployeeList().apply {
                sortWith(Comparator { e1, e2 ->
                    e1.name.compareTo(e2.name)
                })
            }
        }
    val employeeListSortedByRole: List<Employee>
        get() {
            return createEmployeeList().apply {
                sortWith(Comparator { e1, e2 ->
                    e1.role.compareTo(e2.role)
                })
            }
        }

    private fun createEmployeeList(): MutableList<Employee> {
        return mutableListOf(
            Employee(1, "Employee 1", "Developer"),
            Employee(2, "Employee 2", "Tester"),
            Employee(3, "Employee 3", "Support"),
            Employee(4, "Employee 4", "Sales Manager"),
            Employee(5, "Employee 5", "Manager"),
            Employee(6, "Employee 6", "Team lead"),
            Employee(7, "Employee 7", "Scrum Master"),
            Employee(8, "Employee 8", "Sr. Tester"),
            Employee(9, "Employee 9", "Sr. Developer")
        )
    }
}