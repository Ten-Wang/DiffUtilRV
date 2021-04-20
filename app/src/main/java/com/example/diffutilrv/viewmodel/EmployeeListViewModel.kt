package com.example.diffutilrv.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.diffutilrv.model.Employee
import com.example.diffutilrv.model.EmployeeListOrder
import com.example.diffutilrv.repo.EmployeeDataRepository
import kotlinx.coroutines.launch

class EmployeeListViewModel : ViewModel() {
    companion object {
        private val DEFAULT_LIST_ORDER = EmployeeListOrder.SORT_BY_ROLE
    }

    private val repository = EmployeeDataRepository()

    private val _list: MutableLiveData<List<Employee>> = MutableLiveData()
    val list: LiveData<List<Employee>> = _list

    fun fetch(listOrder: EmployeeListOrder? = DEFAULT_LIST_ORDER) {
        viewModelScope.launch {
            _list.value = repository.fetchEmployeeList(listOrder ?: DEFAULT_LIST_ORDER)
        }
    }
}