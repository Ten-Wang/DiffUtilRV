package com.example.diffutilrv.employee.data

object DummyEmployeeDataUtils {

    val employeeListSortedByName: List<Employee>
        get() = employeeList.sortedBy { it.name }

    val employeeListSortedByRole: List<Employee>
        get() = employeeList.sortedBy { it.role }

    private val employeeList: List<Employee>
        get() = listOf(
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