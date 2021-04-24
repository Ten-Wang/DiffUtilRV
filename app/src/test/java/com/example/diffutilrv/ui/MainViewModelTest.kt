package com.example.diffutilrv.ui

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.SavedStateHandle
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.diffutilrv.OneOffEvent
import com.example.diffutilrv.data.Employee
import com.example.diffutilrv.data.EmployeeSortBy
import com.example.diffutilrv.domain.GetEmployeeUseCase
import com.jraska.livedata.test
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.impl.annotations.MockK
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class MainViewModelTest {

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    private lateinit var viewModel: MainViewModel

    @MockK
    private lateinit var getEmployeeUseCase: GetEmployeeUseCase

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
    }

    @Test
    fun `loadData sort by null without saved state`() {
        instantiateMainViewModel()
        val employees = listOf(
            Employee(2, "Employee 2", "Tester"),
            Employee(1, "Employee 1", "Developer"),
        )
        coEvery { getEmployeeUseCase(EmployeeSortBy.ROLE) } returns employees
        val testObserver = viewModel.employees.test()
        viewModel.loadData()
        testObserver.assertValue(employees)
    }

    @Test
    fun `loadData sort by role without saved state`() {
        instantiateMainViewModel()
        val employees = emptyList<Employee>()
        coEvery { getEmployeeUseCase(EmployeeSortBy.ROLE) } returns employees
        val testObserver = viewModel.employees.test()
        viewModel.loadData(EmployeeSortBy.ROLE)
        testObserver.assertValue(employees)
    }

    @Test
    fun `loadData sort by name without saved state`() {
        instantiateMainViewModel()
        val employees = emptyList<Employee>()
        coEvery { getEmployeeUseCase(EmployeeSortBy.NAME) } returns employees
        val testObserver = viewModel.employees.test()
        viewModel.loadData(EmployeeSortBy.NAME)
        testObserver.assertValue(employees)
    }

    @Test
    fun `loadData sort by null with saved state sort by name`() {
        instantiateMainViewModel(EmployeeSortBy.NAME)
        val employees = emptyList<Employee>()
        coEvery { getEmployeeUseCase(EmployeeSortBy.NAME) } returns employees
        val testObserver = viewModel.employees.test()
        viewModel.loadData()
        testObserver.assertValue(employees)
    }

    @Test
    fun `loadData sort by role with saved state sort by name`() {
        instantiateMainViewModel(EmployeeSortBy.NAME)
        val employees = emptyList<Employee>()
        coEvery { getEmployeeUseCase(EmployeeSortBy.ROLE) } returns employees
        val testObserver = viewModel.employees.test()
        viewModel.loadData(EmployeeSortBy.ROLE)
        testObserver.assertValue(employees)
    }

    @Test
    fun `loadData sort by name with saved state sort by role`() {
        instantiateMainViewModel(EmployeeSortBy.ROLE)
        val employees = emptyList<Employee>()
        coEvery { getEmployeeUseCase(EmployeeSortBy.NAME) } returns employees
        val testObserver = viewModel.employees.test()
        viewModel.loadData(EmployeeSortBy.NAME)
        testObserver.assertValue(employees)
    }

    @Test
    fun onClickRow() {
        instantiateMainViewModel()
        val testObserver = viewModel.clickRowAction.test()
        val employee = Employee(8, "Employee 8", "Sr. Tester")
        viewModel.onClickRow(employee)
        testObserver.assertValue(OneOffEvent(employee))
    }

    @Test
    fun onClickRowButton() {
        instantiateMainViewModel()
        val testObserver = viewModel.clickRowButtonAction.test()
        val employee = Employee(6, "Employee 6", "Team lead")
        viewModel.onClickRowButton(employee)
        testObserver.assertValue(OneOffEvent(employee))
    }

    private fun instantiateMainViewModel(sortBy: EmployeeSortBy? = null) {
        val savedStateHandle = if (sortBy == null) {
            SavedStateHandle()
        } else {
            SavedStateHandle(mapOf(STATE_SORT_BY to sortBy))
        }
        viewModel = MainViewModel(savedStateHandle, getEmployeeUseCase)
    }
}