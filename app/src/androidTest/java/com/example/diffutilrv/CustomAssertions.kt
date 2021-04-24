package com.example.diffutilrv

import androidx.recyclerview.widget.RecyclerView
import androidx.test.espresso.ViewAssertion
import androidx.test.espresso.matcher.ViewMatchers
import org.hamcrest.CoreMatchers


/**
 * Assert the number of items inside [RecyclerView].
 *
 * [Reference](https://medium.com/2359media/custom-recyclerview-matcher-and-viewassertion-with-espresso-kotlin-45845c64ab44)
 */
fun withItemCount(count: Int): ViewAssertion = ViewAssertion { view, noViewFoundException ->
    if (noViewFoundException != null) {
        throw noViewFoundException
    }

    if (view !is RecyclerView) {
        throw IllegalStateException("The asserted view is not RecyclerView")
    }

    if (view.adapter == null) {
        throw IllegalStateException("No adapter is assigned to RecyclerView")
    }

    ViewMatchers.assertThat(
        "RecyclerView item count",
        view.adapter?.itemCount,
        CoreMatchers.equalTo(count)
    )
}
