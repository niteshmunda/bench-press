package io.redgreen.benchpress.bmi

import com.google.common.truth.Truth.assertThat
import org.junit.Test

class BmiModelTest {

    @Test
    fun `when weight is default unit changing it makes weight equal to 88 units`() {
        val zeroModel = BmiModel.ZERO
        val weight = 88.0F
        val weightModel = BmiModel(0F, 88.0F, 0F, MeasurementType.METRIC, null)

        assertThat(zeroModel.weightChange(weight).weight)
            .isEqualTo(weightModel.weight)
    }

    @Test
    fun `when height is default unit changing it makes height equal to 180 units`() {
        val zeroModel = BmiModel.ZERO
        val height = 180.0F
        val heightModel = BmiModel(180.0F,0F,0F,MeasurementType.METRIC,null)

        assertThat(zeroModel.heightChange(height).height)
            .isEqualTo(heightModel.height)
    }

    @Test
    fun `when unit is changed from meteric to imperial units`() {
        val zeroMode = BmiModel.ZERO
        val unit = MeasurementType.IMPERIAL
        val unitModel = BmiModel(0F,0F,0F,MeasurementType.IMPERIAL,null)

        assertThat(zeroMode.unitChange(unit).measurementType)
            .isEqualTo(unitModel.measurementType)
    }

    @Test
    fun `when changing the weight changes the BMI` (){
        val defaultModel = BmiModel.DEFAULT
        val weight = 75.0F
        val weightModel = BmiModel(182.5F,75.0F,22.52F,MeasurementType.METRIC,WeightCategory.NORMAL_WEIGHT)

        assertThat(defaultModel.weightChange(weight))
            .isEqualTo(weightModel)
    }

    @Test
    fun `when changing the height changes the BMI`(){
        val defaultModel = BmiModel.DEFAULT
        val height = 175.0F
        val heightModel = BmiModel(175.0F,80.0F,26.12F,MeasurementType.METRIC,WeightCategory.OVER_WEIGHT)

        assertThat(defaultModel.heightChange(height))
            .isEqualTo(heightModel)
    }

    @Test
    fun `when changing the unit system changes the height and weight from METRIC to IMPERIAL` (){
        val metricModel = BmiModel(182.50F,80.0F, 24.0F, MeasurementType.METRIC, WeightCategory.NORMAL_WEIGHT)
        val unit = MeasurementType.IMPERIAL
        val unitModel = BmiModel(71.85F,176.40F,24.0F,MeasurementType.IMPERIAL, WeightCategory.NORMAL_WEIGHT)

        assertThat(metricModel.unitChange(unit))
            .isEqualTo(unitModel)
    }

    @Test
    fun `when changing the unit system changes the height and weight from IMPERIAL to METRIC` (){
        val imperialModel = BmiModel(71.850F,176.40F, 24.0F, MeasurementType.METRIC, WeightCategory.NORMAL_WEIGHT)
        val unit = MeasurementType.METRIC
        val unitModel = BmiModel(182.5F,80.0F,24.0F,MeasurementType.METRIC, WeightCategory.NORMAL_WEIGHT)

        assertThat(imperialModel.unitChange(unit))
            .isEqualTo(unitModel)
    }
}