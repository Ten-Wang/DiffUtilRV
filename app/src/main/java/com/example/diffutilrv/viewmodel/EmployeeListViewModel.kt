package com.example.diffutilrv.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.diffutilrv.model.EmployeeListOrder
import com.example.diffutilrv.model.repo.EmployeeDataRepository
import com.example.diffutilrv.uimodel.EmployeeCheckbox
import kotlinx.coroutines.*

class EmployeeListViewModel(
    private val repository: EmployeeDataRepository,
    private val modelTransformerDispatcher: CoroutineDispatcher = Dispatchers.Main
) : ViewModel() {
    companion object {
        val DEFAULT_LIST_ORDER = EmployeeListOrder.SORT_BY_ROLE
    }

    private val _list: MutableLiveData<Result<List<EmployeeCheckbox>>> = MutableLiveData()
    val list: LiveData<Result<List<EmployeeCheckbox>>> = _list

    private val _isFetching: MutableLiveData<Boolean> = MutableLiveData(false)
    val isFetching: LiveData<Boolean> = _isFetching

    private val listExceptionHandler = CoroutineExceptionHandler { _, throwable ->
        _isFetching.value = false
        if (viewModelScope.isActive) {
            _list.value = Result.failure(throwable)
        }
    }

    fun fetch(listOrder: EmployeeListOrder? = DEFAULT_LIST_ORDER) {
        _isFetching.value = true
        viewModelScope.launch(listExceptionHandler) {
            val listFetcher = async {
                repository.fetchEmployeeList(listOrder ?: DEFAULT_LIST_ORDER)
            }
            val idSetFetcher = async {
                repository.fetchSelectedEmployeeIdSet()
            }
            val list = listFetcher.await()
            val idSet = idSetFetcher.await()
            _list.value = Result.success(
                list.map {
                    async(modelTransformerDispatcher) {
                        EmployeeCheckbox.newInstance(it, idSet)
                    }
                }.awaitAll()
            )
            _isFetching.value = false
        }
    }

    fun updateEmployeeSelected(id: Int, isSelected: Boolean) {
        viewModelScope.launch(CoroutineExceptionHandler { _, throwable -> throwable.printStackTrace() }) {
            if (repository.updateSelectedEmployee(id, isSelected)) {
                // update our list for this change
                _list.value?.getOrNull()?.map {
                    if (it.id == id) {
                        EmployeeCheckbox(it.employee, isSelected)
                    } else {
                        it
                    }
                }?.let {
                    _list.value = Result.success(it)
                }
            }
        }
    }
}