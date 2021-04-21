package com.example.diffutilrv.ui

import androidx.lifecycle.ViewModel
import com.example.diffutilrv.data.Employee
import com.example.diffutilrv.domain.FilterStrategy
import com.example.diffutilrv.domain.GetEmployeesUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.transform
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val getEmployeesUseCase: GetEmployeesUseCase
) : ViewModel() {

    private val filterState = MutableStateFlow(FilterStrategy.ByRole)

    private val _uiState = filterState.transform { strategy ->
        emit(MainUiState.StateLoading)
        try {
            val employees = getEmployeesUseCase(strategy)
            if (employees.isEmpty()) {
                emit(MainUiState.StateEmpty)
            } else {
                emit(MainUiState.StateLoaded(employees))
            }
        } catch (exception: Exception) {
            emit(MainUiState.StateError("Error Occurred!"))
        }
    }

    val uiState: Flow<MainUiState>
        get() = _uiState

    fun filterDataByName() {
        filterState.value = FilterStrategy.ByName
    }

    fun filterDataByRole() {
        filterState.value = FilterStrategy.ByRole
    }
}

sealed class MainUiState {
    object StateLoading : MainUiState()
    object StateEmpty: MainUiState()
    data class StateLoaded(val employees: List<Employee>) : MainUiState()
    data class StateError(val message: String) : MainUiState()
}
