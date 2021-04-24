package com.example.diffutilrv

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel

private const val STATE_SORT_BY = "sortBy"

class MainViewModel(
    private val state: SavedStateHandle,
) : ViewModel() {
    private val _employees = MutableLiveData<List<Employee>>(emptyList())
    val employees: LiveData<List<Employee>> = _employees

    fun loadData(sortBy: EmployeeSortBy? = null) {
        _employees.value = when (sortBy ?: state[STATE_SORT_BY] ?: EmployeeSortBy.ROLE) {
            EmployeeSortBy.ROLE -> DummyEmployeeDataUtils.getEmployeeListSortedByRole()
            EmployeeSortBy.NAME -> DummyEmployeeDataUtils.getEmployeeListSortedByName()
        }
    }

    fun changeSorting(sortBy: EmployeeSortBy) {
        state[STATE_SORT_BY] = sortBy
        loadData(sortBy)
    }
}