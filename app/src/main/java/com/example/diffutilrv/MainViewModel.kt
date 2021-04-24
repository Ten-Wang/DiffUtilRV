package com.example.diffutilrv

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

private const val STATE_SORT_BY = "sortBy"

@HiltViewModel
class MainViewModel @Inject constructor(
    private val state: SavedStateHandle,
    private val dataUtils: DummyEmployeeDataUtils,
) : ViewModel() {
    private val _employees = MutableLiveData<List<Employee>>(emptyList())
    val employees: LiveData<List<Employee>> = _employees
    private val _clickRowAction = MutableLiveData<OneOffEvent<Employee>>()
    val clickRowAction: LiveData<OneOffEvent<Employee>> = _clickRowAction
    private val _clickRowButtonAction = MutableLiveData<OneOffEvent<Employee>>()
    val clickRowButtonAction: LiveData<OneOffEvent<Employee>> = _clickRowButtonAction

    fun loadData(sortBy: EmployeeSortBy? = null) {
        _employees.value = when (sortBy ?: state[STATE_SORT_BY] ?: EmployeeSortBy.ROLE) {
            EmployeeSortBy.ROLE -> dataUtils.getEmployeeListSortedByRole()
            EmployeeSortBy.NAME -> dataUtils.getEmployeeListSortedByName()
        }
    }

    fun changeSorting(sortBy: EmployeeSortBy) {
        state[STATE_SORT_BY] = sortBy
        loadData(sortBy)
    }

    fun onClickRow(employee: Employee) {
        _clickRowAction.value = OneOffEvent(employee)
    }

    fun onClickRowButton(employee: Employee) {
        _clickRowButtonAction.value = OneOffEvent(employee)
    }
}