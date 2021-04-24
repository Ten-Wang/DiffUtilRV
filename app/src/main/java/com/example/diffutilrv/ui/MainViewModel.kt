package com.example.diffutilrv.ui

import androidx.lifecycle.*
import com.example.diffutilrv.OneOffEvent
import com.example.diffutilrv.data.Employee
import com.example.diffutilrv.data.EmployeeSortBy
import com.example.diffutilrv.domain.GetEmployeeUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

internal const val STATE_SORT_BY = "sortBy"

@HiltViewModel
class MainViewModel @Inject constructor(
    private val state: SavedStateHandle,
    private val getEmployeeUseCase: GetEmployeeUseCase,
) : ViewModel() {
    private val _employees = MutableLiveData<List<Employee>>(emptyList())
    val employees: LiveData<List<Employee>> = _employees
    private val _clickRowAction = MutableLiveData<OneOffEvent<Employee>>()
    val clickRowAction: LiveData<OneOffEvent<Employee>> = _clickRowAction
    private val _clickRowButtonAction = MutableLiveData<OneOffEvent<Employee>>()
    val clickRowButtonAction: LiveData<OneOffEvent<Employee>> = _clickRowButtonAction

    fun loadData(sortBy: EmployeeSortBy? = null) {
        viewModelScope.launch {
            _employees.value = getEmployeeUseCase(sortBy ?: state[STATE_SORT_BY] ?: EmployeeSortBy.ROLE)
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