package com.example.diffutilrv.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.diffutilrv.model.Employee
import com.example.diffutilrv.model.EmployeeListOrder
import com.example.diffutilrv.repo.DummyEmployeeDataUtils
import com.example.diffutilrv.repo.EmployeeDataRepository
import kotlinx.coroutines.launch

class EmployeeListViewModel : ViewModel() {
    private val repository = EmployeeDataRepository()

    private val _list: MutableLiveData<List<Employee>> = MutableLiveData()
    val list: LiveData<List<Employee>> = _list

    fun fetch(listOrder: EmployeeListOrder) {
        viewModelScope.launch {
            _list.value = repository.fetchEmployeeList(listOrder)
        }
    }
}