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
import kotlinx.coroutines.delay
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Assert.*
import org.junit.Rule
import org.junit.Test
import java.lang.AssertionError

@ExperimentalCoroutinesApi
class EmployeeListViewModelTest {
    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule() // bcz of viewModelScope

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
        val result = viewModel.result.value
            ?: throw AssertionError("result should not be null")
        assertTrue(result.isSuccess)
        assertSame(fakeList, result.getOrNull())
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
        val result = viewModel.result.value
            ?: throw AssertionError("result should not be null")
        assertTrue(result.isSuccess)
        assertSame(fakeList, result.getOrNull())
    }

    @Test
    fun `when fetch() got an exception expect result failure`() {
        val fakeException = createFakeException()
        val repository = mockk<EmployeeDataRepository>(relaxed = true)
        coEvery { repository.fetchEmployeeList(any()) } throws fakeException

        val viewModel = EmployeeListViewModel(repository)
        viewModel.fetch()

        coVerify(exactly = 1) {
            repository.fetchEmployeeList(any())
        }
        val result = viewModel.result.value
            ?: throw AssertionError("result should not be null")
        assertTrue(result.isFailure)
        assertSame(fakeException, result.exceptionOrNull())
    }

    @Test
    fun `expect the state of isFetching and result are both corresponds to fetch()`() =
        coroutinesTestRule.testDispatcher.runBlockingTest {
            val fakeList = createFakeList()
            val repository = mockk<EmployeeDataRepository>(relaxed = true)
            coEvery { repository.fetchEmployeeList(any()) } coAnswers {
                delay(1)
                fakeList
            }

            val viewModel = EmployeeListViewModel(repository)

            // before
            assertFalse(viewModel.isFetching.value!!)
            assertNull(viewModel.result.value)

            viewModel.fetch()

            // fetching
            coroutinesTestRule.testDispatcher.pauseDispatcher()
            assertTrue(viewModel.isFetching.value!!)
            assertNull(viewModel.result.value)

            // after
            coroutinesTestRule.testDispatcher.resumeDispatcher()
            assertFalse(viewModel.isFetching.value!!)
            assertNotNull(viewModel.result.value)
        }

    private fun createFakeList() = listOf(Employee(1, "fake", "fake"))

    private fun createFakeException() = RuntimeException("fake")
}