package com.example.diffutilrv.ui

import androidx.test.core.app.ActivityScenario
import androidx.test.core.app.launchActivity
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.contrib.RecyclerViewActions.scrollToPosition
import androidx.test.espresso.matcher.ViewMatchers.*
import com.example.diffutilrv.DisableAnimationsRule
import com.example.diffutilrv.R
import com.example.diffutilrv.withItemCount
import com.example.diffutilrv.withRecyclerView
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import org.hamcrest.CoreMatchers.allOf
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test


@HiltAndroidTest
class MainActivityTest {

    @get:Rule(order = 0)
    val hiltRule = HiltAndroidRule(this)

    @get:Rule(order = 1)
    val disableAnimationsRule = DisableAnimationsRule()

    private lateinit var scenario: ActivityScenario<MainActivity>

    @Before
    fun setUp() {
        hiltRule.inject()
    }

    @After
    fun tearDown() {
        scenario.close()
    }

    @Test
    fun defaultSorting() {
        scenario = launchActivity()
        onView(withId(R.id.recycler_view)).check(withItemCount(9))
        checkListItem(0, "Employee 2", "Tester")
        checkListItem(1, "Employee 6", "Team lead")
        checkListItem(2, "Employee 3", "Support")
        checkListItem(3, "Employee 8", "Sr. Tester")
        checkListItem(4, "Employee 9", "Sr. Developer")
        checkListItem(5, "Employee 7", "Scrum Master")
        checkListItem(6, "Employee 4", "Sales Manager")
        checkListItem(7, "Employee 5", "Manager")
        checkListItem(8, "Employee 1", "Developer")
    }

    private fun checkListItem(position: Int, name: String, role: String) {
        scrollTo(position)
        onView(withRecyclerView(R.id.recycler_view).atPosition(position))
            .check(
                matches(
                    allOf(
                        hasDescendant(allOf(withId(R.id.employee_name), withText(name))),
                        hasDescendant(allOf(withId(R.id.employee_role), withText(role))),
                    )
                )
            )
    }

    private fun scrollTo(position: Int) {
        onView(withId(R.id.recycler_view)).perform(scrollToPosition<EmployeeRecyclerViewAdapter.ViewHolder>(position))
    }
}