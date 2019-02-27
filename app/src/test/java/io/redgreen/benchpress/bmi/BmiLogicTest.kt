package io.redgreen.benchpress.bmi

import com.spotify.mobius.test.NextMatchers.hasModel
import com.spotify.mobius.test.NextMatchers.hasNoEffects
import com.spotify.mobius.test.UpdateSpec
import com.spotify.mobius.test.UpdateSpec.assertThatNext
import org.junit.Test

class BmiLogicTest {
    private val updateSpec = UpdateSpec<BmiModel, BmiEvent, Nothing>(BmiLogic::update)

    @Test
    fun `user can change height`() {
        val defaultModel = BmiModel.modelFor(48.0F,160.0F)
        val randomHeight = 180.0F
        updateSpec
            .given(defaultModel)
            .`when`(HeightChangeEvent(randomHeight))
            .then(
                assertThatNext(
                    hasModel(defaultModel.changeHeight(randomHeight)),
                    hasNoEffects()
                )
            )
    }

    @Test
    fun `user can change weight`() {
        val defaultModel = BmiModel.modelFor(48.0F,160.0F)
        val randomWeight = 74.0F

        updateSpec
            .given(defaultModel)
            .`when`(WeightChangeEvent(randomWeight))
            .then(
                assertThatNext(
                    hasModel(defaultModel.changeWeight(randomWeight)),
                    hasNoEffects()
                )
            )
    }

    @Test
    fun `user can change unit system`() {
        val defaultModel = BmiModel.modelFor(48.0F,160.0F)
        val randomUnit = MeasurementType.IMPERIAL

        updateSpec
            .given(defaultModel)
            .`when`(UnitChangeEvent(randomUnit))
            .then(
                assertThatNext(
                    hasModel(defaultModel.changeUnit(randomUnit)),
                    hasNoEffects()
                )
            )
    }
}
