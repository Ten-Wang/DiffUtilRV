package com.example.diffutilrv.veiwmodel;

import androidx.hilt.lifecycle.ViewModelInject;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Transformations;
import androidx.lifecycle.ViewModel;

import com.example.diffutilrv.model.Employee;
import com.example.diffutilrv.usecase.EmployeeUseCase;

import java.util.List;

public class MainViewModel extends ViewModel {

    private final EmployeeUseCase mEmployeeUseCase;
    private final MutableLiveData<EmployeeSortType> mEmployeeSortTypeLiveData;
    private LiveData<List<Employee>> mEmployeeListLiveData;

    @ViewModelInject
    public MainViewModel(EmployeeUseCase employeeUseCase) {
        this.mEmployeeUseCase = employeeUseCase;
        this.mEmployeeSortTypeLiveData = new MutableLiveData<>(EmployeeSortType.ROLE);
    }

    public LiveData<List<Employee>> getEmployeeListLiveData() {
        if (mEmployeeListLiveData == null) {
            mEmployeeListLiveData = Transformations.map(mEmployeeSortTypeLiveData, employeeSortType -> {
                switch (employeeSortType) {
                    case NAME:
                        return mEmployeeUseCase.getEmployeeListSortedByName();
                    case ROLE:
                    default:
                        return mEmployeeUseCase.getEmployeeListSortedByRole();
                }
            });
        }
        return mEmployeeListLiveData;
    }

    public void upDataSortType(EmployeeSortType employeeSortType) {
        this.mEmployeeSortTypeLiveData.postValue(employeeSortType);
    }
}
