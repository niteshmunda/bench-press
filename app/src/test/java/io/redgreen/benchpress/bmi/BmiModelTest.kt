package io.redgreen.benchpress.bmi

import com.google.common.truth.Truth.assertThat
import org.junit.Test

// Try to clarify test names

class BmiModelTest {

    @Test
    fun `when weight is updated, its property is updated`() {
        val defaultModel = BmiModel.modelFor(48.0F,160.0F)
        val weight = 88.0F
        val updatedWeightModel = defaultModel.changeWeight(weight)

        assertThat(updatedWeightModel.weight)
            .isEqualTo(weight)
    }

    @Test
    fun `when height is updated, its property is updated`() {
        val defaultModel = BmiModel.modelFor(48.0F,160.0F)
        val height = 180.0F
        val updatedHeightModel = BmiModel(180.0F,0F,MeasurementType.METRIC)

        assertThat(defaultModel.changeHeight(height).height)
            .isEqualTo(updatedHeightModel.height)
    }

    @Test
    fun `when unit is updated from metric to imperial`() {
        val defaultMode = BmiModel.modelFor(48.0F,160.0F)
        val unit = MeasurementType.IMPERIAL
        val updatedUnitModel = BmiModel(0F,0F,MeasurementType.IMPERIAL)

        assertThat(defaultMode.changeUnit(unit).measurementType)
            .isEqualTo(updatedUnitModel.measurementType)
    }

    @Test
    fun `when updating the weight updates the BMI` (){
        val defaultModel = BmiModel.modelFor(48.0F,160.0F)
        val weight = 75.0F
        val updatedWeightModel = BmiModel(160.0F,75.0F,MeasurementType.METRIC)

        assertThat(defaultModel.changeWeight(weight).bmi)
            .isEqualTo(updatedWeightModel.bmi)
    }

    @Test
    fun `when updating the height updates the BMI`() {
        val defaultModel = BmiModel.modelFor(48.0F,160.0F)
        val height = 175.0F
        val updatedHeightModel = BmiModel(175.0F,48.0F,MeasurementType.METRIC)

        assertThat(defaultModel.changeHeight(height).bmi)
            .isEqualTo(updatedHeightModel.bmi)
    }

    @Test
    fun `when changing the unit system changes the height and weight from METRIC to IMPERIAL` () {
        val metricModel = BmiModel(160.0F,48.0F,  MeasurementType.METRIC)
        val unit = MeasurementType.IMPERIAL
        val imperialModel = BmiModel(160.0F,48.0F,MeasurementType.IMPERIAL)

        assertThat(metricModel.changeUnit(unit))
            .isEqualTo(imperialModel)
    }

    @Test
    fun `when changing the unit system changes the height and weight from IMPERIAL to METRIC` () {
        val imperialModel = BmiModel(160.0F,48.0F,  MeasurementType.IMPERIAL)
        val unit = MeasurementType.METRIC
        val unitModel = BmiModel(160.0F,48.0F,MeasurementType.METRIC)

        assertThat(imperialModel.changeUnit(unit))
            .isEqualTo(unitModel)
    }
}