package com.example.diffutilrv.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.diffutilrv.model.Employee
import com.example.diffutilrv.model.EmployeeListOrder
import com.example.diffutilrv.repo.EmployeeDataRepository
import com.example.diffutilrv.utils.CoroutinesTestRule
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.Assert
import org.junit.Rule
import org.junit.Test

class EmployeeListViewModelTest {
    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule() // bcz of viewModelScope

    @ExperimentalCoroutinesApi
    @get:Rule
    var coroutinesTestRule = CoroutinesTestRule() // bcz of Coroutines

    @Test
    fun `when fetch() doesn't pass EmployeeListOrder expect using default`() {
        val defaultOrder = EmployeeListViewModel.DEFAULT_LIST_ORDER
        val fakeList = createFakeList()
        val repository = mockk<EmployeeDataRepository>(relaxed = true)
        coEvery { repository.fetchEmployeeList(defaultOrder) } returns fakeList

        val viewModel = EmployeeListViewModel(repository)
        viewModel.fetch()

        coVerify(exactly = 1) {
            repository.fetchEmployeeList(defaultOrder)
        }
        Assert.assertSame(fakeList, viewModel.list.value)
    }

    @Test
    fun `when fetch() passes EmployeeListOrder expect using it`() {
        val nonDefaultOrder: EmployeeListOrder = EmployeeListOrder.values()
            .find { it != EmployeeListViewModel.DEFAULT_LIST_ORDER }!!
        val fakeList = createFakeList()
        val repository = mockk<EmployeeDataRepository>(relaxed = true)
        coEvery { repository.fetchEmployeeList(nonDefaultOrder) } returns fakeList

        val viewModel = EmployeeListViewModel(repository)
        viewModel.fetch(nonDefaultOrder)

        coVerify(exactly = 1) {
            repository.fetchEmployeeList(nonDefaultOrder)
        }
        Assert.assertSame(fakeList, viewModel.list.value)
    }

    private fun createFakeList() = listOf(Employee(1, "fake", "fake"))
}