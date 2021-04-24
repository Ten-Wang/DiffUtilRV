package com.example.diffutilrv

object DummyEmployeeDataUtils {
    private val employeeList: List<Employee> = listOf(
        Employee(1, "Employee 1", "Developer"),
        Employee(2, "Employee 2", "Tester"),
        Employee(3, "Employee 3", "Support"),
        Employee(4, "Employee 4", "Sales Manager"),
        Employee(5, "Employee 5", "Manager"),
        Employee(6, "Employee 6", "Team lead"),
        Employee(7, "Employee 7", "Scrum Master"),
        Employee(8, "Employee 8", "Sr. Tester"),
        Employee(9, "Employee 9", "Sr. Developer"),
    )

    fun getEmployeeListSortedByName(): List<Employee> = employeeList.sortedBy { it.name }

    fun getEmployeeListSortedByRole(): List<Employee> = employeeList.sortedByDescending { it.role }
}