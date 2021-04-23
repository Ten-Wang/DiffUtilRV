package com.example.diffutilrv.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.diffutilrv.model.repo.EmployeeDataRepository

class EmployeeViewModelFactory(
    private val repository: EmployeeDataRepository
) : ViewModelProvider.NewInstanceFactory() {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        @Suppress("UNCHECKED_CAST")
        return EmployeeListViewModel(repository) as T
    }
}