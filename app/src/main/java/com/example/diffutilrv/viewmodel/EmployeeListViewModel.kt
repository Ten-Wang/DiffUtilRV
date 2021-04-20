package com.example.diffutilrv.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.diffutilrv.model.Employee
import com.example.diffutilrv.repo.DummyEmployeeDataUtils

class EmployeeListViewModel: ViewModel() {
    private val _list: MutableLiveData<List<Employee>> = MutableLiveData()
    val list: LiveData<List<Employee>> = _list

    enum class ListOrder {
        SORT_BY_NAME, SORT_BY_ROLE
    }

    fun fetch(listOrder: ListOrder) {
        when (listOrder) {
            ListOrder.SORT_BY_NAME -> {
                _list.value = DummyEmployeeDataUtils.employeeListSortedByName
            }
            ListOrder.SORT_BY_ROLE -> {
                _list.value = DummyEmployeeDataUtils.employeeListSortedByRole
            }
        }
    }
}