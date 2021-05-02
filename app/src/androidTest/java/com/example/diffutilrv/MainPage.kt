package com.example.diffutilrv

import androidx.test.ext.junit.rules.ActivityScenarioRule
import com.example.diffutilrv.util.checkAllViewsDisplayed
import com.example.diffutilrv.util.checkSortingInRecyclerView
import com.example.diffutilrv.util.clickActionBarMenuWithText
import com.example.diffutilrv.util.page.Page

class MainPage : Page() {

    companion object {
        fun createRule(): ActivityScenarioRule<MainActivity> =
            ActivityScenarioRule(MainActivity::class.java)
    }

    override fun verify(): Page {
        checkAllViewsDisplayed(
            R.id.recycler_view
        )
        return this
    }

    fun checkDefaultSorting(): MainPage {
        checkSortingInRecyclerView(
            rule = rule!!,
            recyclerViewId = R.id.recycler_view,
            itemTextViewId = R.id.employee_role, // default by role
            itemTransformation = { it.toString() })
        return this
    }

    fun clickSortByName(): MainPage {
        clickActionBarMenuWithText(R.string.sort_by_name)
        checkSortingInRecyclerView(
            rule = rule!!,
            recyclerViewId = R.id.recycler_view,
            itemTextViewId = R.id.employee_name,
            itemTransformation = { it.toString() })
        return this
    }

    fun clickSortByCost(): MainPage {
        clickActionBarMenuWithText(R.string.sort_by_cost)
        checkSortingInRecyclerView(
            rule = rule!!,
            recyclerViewId = R.id.recycler_view,
            itemTextViewId = R.id.employee_cost,
            itemTransformation = { it.toString().toInt() })
        return this
    }

    fun clickSortByRole(): MainPage {
        clickActionBarMenuWithText(R.string.sort_by_role)
        checkSortingInRecyclerView(
            rule = rule!!,
            recyclerViewId = R.id.recycler_view,
            itemTextViewId = R.id.employee_role,
            itemTransformation = { it.toString() })
        return this
    }
}