package com.example.diffutilrv.util

import android.app.Activity
import android.widget.TextView
import androidx.annotation.IdRes
import androidx.annotation.StringRes
import androidx.recyclerview.widget.RecyclerView
import androidx.test.espresso.Espresso.*
import androidx.test.espresso.action.ViewActions.*
import androidx.test.espresso.assertion.*
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.platform.app.InstrumentationRegistry
import org.junit.Assert

fun checkAllViewsDisplayed(vararg viewIds: Int) {
    viewIds.forEach { viewId ->
        onView(withId(viewId))
            .check(ViewAssertions.matches(isDisplayed()))
    }
}

fun clickActionBarMenuWithText(@StringRes textId: Int) {
    openActionBarOverflowOrOptionsMenu(InstrumentationRegistry.getInstrumentation().targetContext)
    clickViewWithText(textId)
}

fun clickViewWithText(@StringRes textId: Int) {
    onView(withText(textId)).perform(click())
}

fun <T : Comparable<T>> checkSortingInRecyclerView(
    rule: ActivityScenarioRule<*>,
    @IdRes recyclerViewId: Int,
    @IdRes itemTextViewId: Int,
    itemTransformation: (CharSequence) -> T
) {
    rule.scenario.onActivity {
        val collection =
            collectItemsInRecyclerView(it, recyclerViewId, itemTextViewId, itemTransformation)
        Assert.assertTrue(collection.isNotEmpty())
        Assert.assertEquals(collection.sorted(), collection)
    }
}

fun <T> collectItemsInRecyclerView(
    activity: Activity,
    @IdRes recyclerViewId: Int,
    @IdRes itemTextViewId: Int,
    itemTransformation: (CharSequence) -> T
): List<T> {
    val rv: RecyclerView = activity.findViewById(recyclerViewId)
    WaitingForRecyclerView().startWait(rv)
    Assert.assertTrue(rv.childCount > 0)
    val collections = mutableListOf<T>()
    for (i in 0..rv.childCount) {
        rv.getChildAt(i)?.findViewById<TextView>(itemTextViewId)?.text?.let { text ->
            collections.add(itemTransformation(text))
        }
    }
    Assert.assertTrue(collections.size > 0)
    return collections
}