package com.example.diffutilrv.repository;

import com.example.diffutilrv.model.Employee;

import java.util.List;

public interface EmployeeRepository {
    List<Employee> getEmployeeList();
    List<Employee> getEmployeeListSortedByRole();
    List<Employee> getEmployeeListSortedByName();
}
