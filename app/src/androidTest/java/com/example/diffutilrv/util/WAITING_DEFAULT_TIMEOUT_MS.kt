package com.example.diffutilrv.util

import androidx.recyclerview.widget.RecyclerView
import androidx.test.espresso.IdlingResource

const val WAITING_DEFAULT_TIMEOUT_MS = 1000L

class WaitingForRecyclerView : IdlingResource {
    var recyclerView: RecyclerView? = null

    var startTime = 0L
    var timeout = 0L

    @Volatile
    private var callback: IdlingResource.ResourceCallback? = null

    fun startWait(recyclerView: RecyclerView, timeout: Long = WAITING_DEFAULT_TIMEOUT_MS) {
        this.recyclerView = recyclerView
        this.startTime = System.currentTimeMillis()
        this.timeout = timeout
    }

    override fun getName(): String = javaClass.name

    override fun isIdleNow(): Boolean {
        val hasChildView = recyclerView?.let {
            (it.adapter?.itemCount ?: 0) > 0 && it.childCount > 0
        } ?: false
        if (!hasChildView) {
            callback?.onTransitionToIdle()
            val isTimeout =
                startTime != 0L && timeout != 0L && (System.currentTimeMillis() - startTime >= timeout)
            if (isTimeout) {
                return true
            }
        }
        return hasChildView
    }

    override fun registerIdleTransitionCallback(callback: IdlingResource.ResourceCallback?) {
        this.callback = callback
    }
}