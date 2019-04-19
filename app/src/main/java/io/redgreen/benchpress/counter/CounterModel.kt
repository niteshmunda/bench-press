package io.redgreen.benchpress.counter

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class CounterModel(
    val counter: Int
) : Parcelable {
    companion object {
        val ZERO = CounterModel(0)
    }

    fun increment(): CounterModel =
        copy(counter = (counter + 1) % 11)

    fun decrement(): CounterModel =
        copy(counter = if (counter == 0) 10 else counter - 1)
}
