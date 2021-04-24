package com.example.diffutilrv.model

enum class EmployeeListOrder(val comparator: Comparator<Employee>) {
    SORT_BY_NAME(Comparator { o1, o2 -> o1.name.compareTo(o2.name) }),
    SORT_BY_ROLE(Comparator { o1, o2 -> o1.role.compareTo(o2.role) }),
    SORT_BY_COST(Comparator { o1, o2 -> o1.cost.compareTo(o2.cost) });

    fun sort(collection: Collection<Employee>): List<Employee> {
        return collection.sortedWith(this.comparator)
    }
}