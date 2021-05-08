package com.example.diffutilrv.veiwmodel;

import androidx.hilt.lifecycle.ViewModelInject;
import androidx.lifecycle.ViewModel;

import com.example.diffutilrv.model.Employee;
import com.example.diffutilrv.usecase.EmployeeUseCase;

import java.util.List;

public class MainViewModel extends ViewModel {

    private final EmployeeUseCase mEmployeeUseCase;

    @ViewModelInject
    public MainViewModel(EmployeeUseCase employeeUseCase) {
        this.mEmployeeUseCase = employeeUseCase;
    }

    public List<Employee> getEmployeeListSortedByRole(){
        return mEmployeeUseCase.getEmployeeListSortedByRole();
    }

    public List<Employee> getEmployeeListSortedByName(){
        return mEmployeeUseCase.getEmployeeListSortedByName();
    }
}
