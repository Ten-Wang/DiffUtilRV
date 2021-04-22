package com.example.diffutilrv.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.diffutilrv.enum.FilterType
import com.example.diffutilrv.model.Employee
import com.example.diffutilrv.uistate.StatefulLiveData
import com.example.diffutilrv.uistate.StatefulMutableLiveData
import com.example.diffutilrv.uistate.UiState
import com.example.diffutilrv.usecase.GetEmployeeListUseCase
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch

class EmployeeViewModel(private val getEmployeeListUseCase: GetEmployeeListUseCase) : ViewModel() {

    val employeeList: StatefulLiveData<List<Employee>>
        get() = _employeeList
    private val _employeeList = StatefulMutableLiveData<List<Employee>>()

    fun fetchEmployeeListByName() {
        return fetchEmployeeList(FilterType.Name)
    }

    fun fetchEmployeeListByRole() {
        return fetchEmployeeList(FilterType.Role)
    }

    private fun fetchEmployeeList(filterType: FilterType) {
        viewModelScope.launch {
            getEmployeeListUseCase(filterType)
                .onStart { _employeeList.value = UiState.showLoading() }
                .onEach { _employeeList.value = UiState.hideLoading() }
                .catch { _employeeList.value = UiState.error(it) }
                .collect { _employeeList.value = UiState.successOrEmpty(it) }
        }
    }
}