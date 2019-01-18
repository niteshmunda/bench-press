package io.redgreen.benchpress.counter

import com.spotify.mobius.test.NextMatchers.hasModel
import com.spotify.mobius.test.NextMatchers.hasNoEffects
import com.spotify.mobius.test.UpdateSpec
import com.spotify.mobius.test.UpdateSpec.assertThatNext
import org.junit.Test

class CounterLogicTest {
    private val updateSpec = UpdateSpec<CounterModel, CounterEvent, Nothing>(CounterLogic::update)

    @Test
    fun `user can increment the counter`() {
        val zeroModel = CounterModel.ZERO

        updateSpec
            .given(zeroModel)
            .`when`(IncrementEvent)
            .then(
                assertThatNext(
                    hasModel(zeroModel.increment()),
                    hasNoEffects()
                )
            )
    }

    @Test
    fun `user can decrement the counter`() {
        val zeroModel = CounterModel.ZERO

        updateSpec
            .given(zeroModel)
            .`when`(DecrementEvent)
            .then(
                assertThatNext(
                    hasModel(zeroModel.decrement()),
                    hasNoEffects()
                )
            )
    }
}
