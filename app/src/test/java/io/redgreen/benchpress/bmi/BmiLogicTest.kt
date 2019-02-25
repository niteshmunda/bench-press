package io.redgreen.benchpress.bmi

import com.spotify.mobius.test.NextMatchers.hasModel
import com.spotify.mobius.test.NextMatchers.hasNoEffects
import com.spotify.mobius.test.UpdateSpec
import com.spotify.mobius.test.UpdateSpec.assertThatNext
import org.junit.Test

class BmiLogicTest{
    private val updateSpec = UpdateSpec<BmiModel, BmiEvent, Nothing>(BmiLogic::update)

    @Test
    fun `when user can change the height`(){
        val defaultModel = BmiModel.DEFAULT
        val randomHeight = 180.0F
        updateSpec
            .given(defaultModel)
            .`when`(HeightChangeEvent(randomHeight))
            .then(
                assertThatNext(
                    hasModel(defaultModel.heightChange(randomHeight)),
                    hasNoEffects()
                )
            )

    }

    @Test
    fun `when user can change the weight`(){
        val defaultModel = BmiModel.DEFAULT
        val randomWeight = 74.0F

        updateSpec
            .given(defaultModel)
            .`when`(WeightChangeEvent(randomWeight))
            .then(
                assertThatNext(
                    hasModel(defaultModel.weightChange(randomWeight)),
                    hasNoEffects()
                )
            )
    }

    @Test
    fun `when user can change unit system`(){
        val defaultModel = BmiModel.DEFAULT
        val randomUnit = MeasurementType.IMPERIAL

        updateSpec
            .given(defaultModel)
            .`when`(UnitChangeEvent(randomUnit))
            .then(
                assertThatNext(
                    hasModel(defaultModel.unitChange(randomUnit)),
                    hasNoEffects()
                )
            )
    }
}
