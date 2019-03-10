package io.redgreen.benchpress.counter

import com.google.common.truth.Truth.assertThat
import org.junit.Test

class CounterModelTest {
    @Test
    fun `when counter value is '10', then increment will make it zero`() {
        val tenModel = CounterModel(10)

        assertThat(tenModel.increment())
            .isEqualTo(CounterModel.ZERO)
    }

    @Test
    fun `when counter value is '0', then decrementing makes it 10`() {
        val zeroModel = CounterModel.ZERO
        val tenModel = CounterModel(10)

        assertThat(zeroModel.decrement())
            .isEqualTo(tenModel)
    }

    @Test
    fun `when counter value is '5', then incrementing makes it 6`() {
        val fiveModel = CounterModel(5)
        val sixModel = CounterModel(6)

        assertThat(fiveModel.increment())
            .isEqualTo(sixModel)
    }

    @Test
    fun `when counter value is decremented from '10', then it becomes 9`() {
        val tenModel = CounterModel(10)
        val nineModel = CounterModel(9)

        assertThat(tenModel.decrement())
            .isEqualTo(nineModel)
    }
}