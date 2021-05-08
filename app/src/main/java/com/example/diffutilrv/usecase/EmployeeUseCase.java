package com.example.diffutilrv.usecase;

import com.example.diffutilrv.model.Employee;
import com.example.diffutilrv.repository.EmployeeRepository;

import java.util.List;

import javax.inject.Inject;

public class EmployeeUseCase {
    EmployeeRepository mEmployeeRepository;

    @Inject
    public EmployeeUseCase(EmployeeRepository employeeRepository) {
        this.mEmployeeRepository = employeeRepository;
    }


    public List<Employee> getEmployeeListSortedByRole() {
        return this.mEmployeeRepository.getEmployeeListSortedByRole();
    }

    public List<Employee> getEmployeeListSortedByName() {
        return this.mEmployeeRepository.getEmployeeListSortedByName();
    }

}
