package com.example.diffutilrv.util.page

import android.os.SystemClock
import androidx.test.espresso.Espresso
import androidx.test.ext.junit.rules.ActivityScenarioRule

/**
 * improved from https://hanru-yeh.medium.com/pageobject-pattern-by-kotlin-into-android-ui-tests-1df8e0b72b69
 */
open class Page {
    companion object {
        inline fun <reified T : Page> on(verify: Boolean = true): T {
            return Page().on(verify)
        }
    }

    inline fun <reified T : Page> on(verify: Boolean = true): T {
        val page = T::class.constructors.first().call()
        if (verify) {
            page.verify()
        }
        return page
    }

    open fun verify(): Page {
        assert(false)
        return this
    }

    /**
     * could be null because a PageObject might not be tied with an Activity
     */
    var rule: ActivityScenarioRule<*>? = null

    fun acquireRule(): ActivityScenarioRule<*> =
        rule ?: throw IllegalStateException("Page: should set rule() before use")
}

fun <T : Page> T.rule(rule: ActivityScenarioRule<*>): T {
    this.rule = rule
    return this
}

fun <T : Page> T.back(): T {
    Espresso.pressBack()
    return this
}

fun <T : Page> T.wait(milliseconds: Long): T {
    SystemClock.sleep(milliseconds)
    return this
}

fun <T : Page> T.runWith(block: () -> Unit): T {
    block.invoke()
    return this
}
