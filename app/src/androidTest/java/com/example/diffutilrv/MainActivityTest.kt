package com.example.diffutilrv

import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.LargeTest
import com.example.diffutilrv.util.page.Page
import com.example.diffutilrv.util.page.rule

import org.junit.Test
import org.junit.runner.RunWith

import org.junit.Rule

@RunWith(AndroidJUnit4::class)
@LargeTest
class MainActivityTest {
    @get:Rule
    var rule = MainPage.createRule()

    @Test
    fun testSwitchSorting() {
        Page.on<MainPage>()
            .rule(rule)
            .checkDefaultSorting()
            .clickSortByName()
            .clickSortByCost()
            .clickSortByRole()
    }
}
