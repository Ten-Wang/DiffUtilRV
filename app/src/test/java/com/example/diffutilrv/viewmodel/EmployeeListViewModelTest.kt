package com.example.diffutilrv.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.diffutilrv.model.Employee
import com.example.diffutilrv.model.EmployeeListOrder
import com.example.diffutilrv.model.repo.EmployeeDataRepository
import com.example.diffutilrv.uimodel.EmployeeCheckbox
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
        val fakeIdSet = createFakeIdSet()
        val fakeList = createFakeList()
        val repository = mockk<EmployeeDataRepository>(relaxed = true)
        coEvery { repository.fetchEmployeeList(defaultOrder) } returns fakeList
        coEvery { repository.fetchSelectedEmployeeIdSet() } returns fakeIdSet

        val viewModel = EmployeeListViewModel(repository)
        viewModel.fetch()

        coVerify(exactly = 1) {
            repository.fetchEmployeeList(defaultOrder)
            repository.fetchSelectedEmployeeIdSet()
        }
        val result = viewModel.list.value
            ?: throw AssertionError("result should not be null")
        assertTrue(result.isSuccess)
        assertEquals(
            fakeList.map { EmployeeCheckbox.newInstance(it, fakeIdSet) },
            result.getOrNull()
        )
    }

    @Test
    fun `when fetch() passes EmployeeListOrder expect using it`() {
        val nonDefaultOrder: EmployeeListOrder = EmployeeListOrder.values()
            .find { it != EmployeeListViewModel.DEFAULT_LIST_ORDER }!!
        val fakeIdSet = createFakeIdSet()
        val fakeList = createFakeList()
        val repository = mockk<EmployeeDataRepository>(relaxed = true)
        coEvery { repository.fetchEmployeeList(nonDefaultOrder) } returns fakeList
        coEvery { repository.fetchSelectedEmployeeIdSet() } returns fakeIdSet

        val viewModel = EmployeeListViewModel(repository)
        viewModel.fetch(nonDefaultOrder)

        coVerify(exactly = 1) {
            repository.fetchEmployeeList(nonDefaultOrder)
            repository.fetchSelectedEmployeeIdSet()
        }
        val result = viewModel.list.value
            ?: throw AssertionError("result should not be null")
        assertTrue(result.isSuccess)
        assertEquals(
            fakeList.map { EmployeeCheckbox.newInstance(it, fakeIdSet) },
            result.getOrNull()
        )
    }

    @Test
    fun `when fetch() got an exception from fetchEmployeeList expect result failure`() {
        val fakeException = createFakeException()
        val repository = mockk<EmployeeDataRepository>(relaxed = true)
        coEvery { repository.fetchEmployeeList(any()) } throws fakeException

        val viewModel = EmployeeListViewModel(repository)
        viewModel.fetch()

        coVerify(exactly = 1) {
            repository.fetchEmployeeList(any())
        }
        val result = viewModel.list.value
            ?: throw AssertionError("result should not be null")
        assertTrue(result.isFailure)
        assertSame(fakeException, result.exceptionOrNull())
    }

    @Test
    fun `when fetch() got an exception from fetchSelectedEmployeeIdSet expect result failure`() {
        val fakeException = createFakeException()
        val repository = mockk<EmployeeDataRepository>(relaxed = true)
        coEvery { repository.fetchSelectedEmployeeIdSet() } throws fakeException

        val viewModel = EmployeeListViewModel(repository)
        viewModel.fetch()

        coVerify(exactly = 1) {
            repository.fetchSelectedEmployeeIdSet()
        }
        val result = viewModel.list.value
            ?: throw AssertionError("result should not be null")
        assertTrue(result.isFailure)
        assertSame(fakeException, result.exceptionOrNull())
    }

    @Test
    fun `expect the state of isFetching and result both correspond to fetch()`() =
        coroutinesTestRule.testDispatcher.runBlockingTest {
            val fakeIdSet = createFakeIdSet()
            val fakeList = createFakeList()
            val repository = mockk<EmployeeDataRepository>(relaxed = true)
            coEvery { repository.fetchEmployeeList(any()) } coAnswers {
                delay(1)
                fakeList
            }
            coEvery { repository.fetchSelectedEmployeeIdSet() } coAnswers {
                fakeIdSet
            }

            val viewModel = EmployeeListViewModel(
                repository,
                modelTransformerDispatcher = coroutinesTestRule.testDispatcher
            )

            // before
            assertFalse(viewModel.isFetching.value!!)
            assertNull(viewModel.list.value)

            viewModel.fetch()

            // fetching
            coroutinesTestRule.testDispatcher.pauseDispatcher()
            assertTrue(viewModel.isFetching.value!!)
            assertNull(viewModel.list.value)

            // after
            coroutinesTestRule.testDispatcher.resumeDispatcher()
            assertFalse(viewModel.isFetching.value!!)
            assertNotNull(viewModel.list.value)
        }

    @Test
    fun `when updateEmployeeSelected() success expect also updates the item in the list`() {
        val targetId = 1
        val fakeIdSet = emptySet<Int>()
        val fakeList = listOf(Employee(targetId, "", "", 0))
        val repository = mockk<EmployeeDataRepository>()
        coEvery { repository.fetchSelectedEmployeeIdSet() } returns fakeIdSet
        coEvery { repository.fetchEmployeeList(any()) } returns fakeList
        coEvery { repository.updateSelectedEmployee(any(), any()) } returns true

        val viewModel = EmployeeListViewModel(repository)
        viewModel.fetch()
        assertFalse(viewModel.list.value!!.getOrThrow().first().isChecked)
        viewModel.updateEmployeeSelected(targetId, true)
        assertTrue(viewModel.list.value!!.getOrThrow().first().isChecked)
    }

    @Test
    fun `when updateEmployeeSelected() failed expect nothing updated`() {
        val targetId = 1
        val fakeIdSet = emptySet<Int>()
        val fakeList = listOf(Employee(targetId, "", "", 0))
        val repository = mockk<EmployeeDataRepository>()
        coEvery { repository.fetchSelectedEmployeeIdSet() } returns fakeIdSet
        coEvery { repository.fetchEmployeeList(any()) } returns fakeList
        coEvery { repository.updateSelectedEmployee(any(), any()) } returns false

        val viewModel = EmployeeListViewModel(repository)
        viewModel.fetch()
        assertFalse(viewModel.list.value!!.getOrThrow().first().isChecked)
        viewModel.updateEmployeeSelected(targetId, true)
        assertFalse(viewModel.list.value!!.getOrThrow().first().isChecked)
    }

    @Test
    fun `when updateEmployeeSelected() got an exception expect nothing updated`() {
        val targetId = 1
        val fakeIdSet = emptySet<Int>()
        val fakeList = listOf(Employee(targetId, "", "", 0))
        val fakeException = createFakeException()
        val repository = mockk<EmployeeDataRepository>()
        coEvery { repository.fetchSelectedEmployeeIdSet() } returns fakeIdSet
        coEvery { repository.fetchEmployeeList(any()) } returns fakeList
        coEvery { repository.updateSelectedEmployee(any(), any()) } throws fakeException

        val viewModel = EmployeeListViewModel(repository)
        viewModel.fetch()
        assertFalse(viewModel.list.value!!.getOrThrow().first().isChecked)
        viewModel.updateEmployeeSelected(targetId, true)
        assertFalse(viewModel.list.value!!.getOrThrow().first().isChecked)
    }

    private fun createFakeList() = listOf(Employee(1, "fake", "fake", 0))

    private fun createFakeIdSet() = setOf(1)

    private fun createFakeException() = RuntimeException("fake")
}