package com.example.diffutilrv.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.diffutilrv.model.Employee
import com.example.diffutilrv.model.EmployeeListOrder
import com.example.diffutilrv.model.repo.EmployeeDataRepository
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch

class EmployeeListViewModel(
    private val repository: EmployeeDataRepository
) : ViewModel() {
    companion object {
        val DEFAULT_LIST_ORDER = EmployeeListOrder.SORT_BY_ROLE
    }

    private val _result: MutableLiveData<Result<List<Employee>>> = MutableLiveData()
    val result: LiveData<Result<List<Employee>>> = _result

    private val _isFetching: MutableLiveData<Boolean> = MutableLiveData(false)
    val isFetching: LiveData<Boolean> = _isFetching

    private val exceptionHandler = CoroutineExceptionHandler { _, throwable ->
        _isFetching.value = false
        if (viewModelScope.isActive) {
            _result.value = Result.failure(throwable)
        }
    }

    fun fetch(listOrder: EmployeeListOrder? = DEFAULT_LIST_ORDER) {
        _isFetching.value = true
        viewModelScope.launch(exceptionHandler) {
            _result.value = Result.success(
                repository.fetchEmployeeList(listOrder ?: DEFAULT_LIST_ORDER)
            )
            _isFetching.value = false
        }
    }
}