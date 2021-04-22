package com.example.diffutilrv

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class EmployeeViewModel : ViewModel() {

    private val _list = MutableLiveData<List<Employee>>()
    val list: LiveData<List<Employee>> = _list

    fun sortByName() {
        _list.value = DummyEmployeeDataUtils.employeeListSortedByName
    }

    fun sortByRole() {
        _list.value = DummyEmployeeDataUtils.employeeListSortedByRole
    }
}