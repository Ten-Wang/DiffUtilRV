package com.example.diffutilrv.model.repo

import com.example.diffutilrv.model.Employee
import com.example.diffutilrv.model.EmployeeListOrder
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.util.Random
import kotlin.math.roundToInt

class DummyEmployeeDataRepository(
    private val workerDispatcher: CoroutineDispatcher = Dispatchers.IO
) : EmployeeDataRepository {

    private val list by lazy { createEmployeeList() }
    private val selectedIdSet by lazy { createSelectedIdSet() }

    override suspend fun fetchEmployeeList(listOrder: EmployeeListOrder): List<Employee> =
        withContext(workerDispatcher) {
            //delay(500) // fake network latency; cooperative cancellation
            //throw RuntimeException("fake exception from fetch list")
            listOrder.sort(list)
        }

    private fun createEmployeeList(): MutableList<Employee> {
        val headcount = mutableMapOf(
            "Developer" to 9,
            "Tester" to 2,
            "Support" to 4,
            "Sales Manager" to 6,
            "Manager" to 2,
            "Team lead" to 3,
            "Scrum Master" to 3,
            "Sr. Tester" to 1,
            "Sr. Developer" to 6
        )
        val budget = mutableMapOf(
            "Developer" to 60,
            "Tester" to 50,
            "Support" to 45,
            "Sales Manager" to 50,
            "Manager" to 130,
            "Team lead" to 120,
            "Scrum Master" to 90,
            "Sr. Tester" to 80,
            "Sr. Developer" to 90
        )

        return MutableList(headcount.values.sum()) {
            val role = headcount.keys.random()
            val count = headcount[role]
            if (count == 1) { // last one
                headcount.remove(role)
            } else {
                headcount[role] = count!! - 1
            }
            val base = budget[role]!! * 1000
            val factor = ((100 + Random().nextInt(31)) / 100f)
            val cost = (base * factor).roundToInt() / 1000
            Employee(it, "Employee ${String.format("%03d", it + 1)}", role, cost)
        }
    }

    override suspend fun fetchSelectedEmployeeIdSet(): Set<Int> =
        withContext(workerDispatcher) {
            //delay(500) // fake latency
            //throw RuntimeException("fake exception from fetch id set")
            selectedIdSet
        }

    override suspend fun updateSelectedEmployee(id: Int, isSelected: Boolean): Boolean =
        withContext(workerDispatcher) {
            if (isSelected) {
                selectedIdSet.add(id)
            } else {
                selectedIdSet.remove(id)
            }
            true
        }

    private fun createSelectedIdSet(): MutableSet<Int> {
        return mutableSetOf(0, 1, 2)
    }
}