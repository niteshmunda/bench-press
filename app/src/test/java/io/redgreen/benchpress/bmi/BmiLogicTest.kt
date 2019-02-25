package io.redgreen.benchpress.bmi

import com.spotify.mobius.test.NextMatchers.hasModel
import com.spotify.mobius.test.NextMatchers.hasNoEffects
import com.spotify.mobius.test.UpdateSpec
import com.spotify.mobius.test.UpdateSpec.assertThatNext
import org.junit.Test

class BmiLogicTest{
    private val updateSpec = UpdateSpec<BmiModel, BmiEvent, Nothing>(BmiLogic::update)

    @Test
    fun `when user slides the height seekbar`(){
        val normalModel = BmiModel.DEFAULT
//        val height = 1.80
        updateSpec
            .given(normalModel)
            .`when`(HeightChangeEvent(height))
            .then(
                assertThatNext(
                    hasModel(normalModel.heightChange(height)),
                    hasNoEffects()
                )
            )

    }

    @Test
    fun `when user slides the weight seekbar`(){
        val normalModel = BmiModel.DEFAULT
        val weight = 72
        updateSpec
            .given(normalModel)
            .`when`(WeightChangeEvent(weight))
            .then(
                assertThatNext(
                    hasModel(normalModel.weightChange(weight)),
                    hasNoEffects()
                )
            )
    }

    @Test
    fun `when user changes unit to si unit from non si unit`(){
        val normalModel = BmiModel.DEFAULT

        updateSpec
            .given(normalModel)
            .`when`(UnitChangeEvent)
            .then(
                assertThatNext(
                    hasModel(normalModel.unitChange()),
                    hasNoEffects()
                )
            )
    }
}
