package io.redgreen.benchpress.bmi

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

// 1. Use computed properties.
// 2. Model should always use a 'val'.
// 3. Min / max height and weight should be set from resources file (use an integer resource). (i.e.) Use a single configuration source.
// 4. Clean up primitive obsession on weight and height. (Thereby resolving the precision loss problem that we have now.)

@Parcelize
data class BmiModel(
    var height: Float,
    var weight: Float,
    var measurementType: MeasurementType
) : Parcelable {
    companion object {
        val DEFAULT = BmiModel(
            height = 160.0F,
            weight = 48.0F,
            measurementType = MeasurementType.METRIC
        )
    }

    fun getBmi(): Float {
        var bmi = ((weight / (height * height) * 10000))
        val factor = Math.pow(10.0, 2.0)
        bmi = (Math.round(bmi * factor) / factor).toFloat()
        return bmi
    }

    fun getWeightCategory(): String {
        val bmi = getBmi()
        return when {
            bmi <= 18.5 -> WeightCategory.UNDERWEIGHT.category
            bmi > 18.5 && bmi <= 25 -> WeightCategory.NORMAL_WEIGHT.category
            bmi > 25 && bmi < 30 -> WeightCategory.OVER_WEIGHT.category
            else -> WeightCategory.OBESE.category
        }
    }

    fun getTheHeight(): Float {
        val factor = Math.pow(10.0, 2.0)
        return if (measurementType == MeasurementType.METRIC) {
            (Math.round(height * factor) / factor).toFloat()
        } else {
            (Math.round((height / 2.54F) * factor) / factor).toFloat()
        }
    }

    fun getTheWeight(): Float {
        val factor = Math.pow(10.0, 2.0)
        return if(measurementType == MeasurementType.METRIC) {
            (Math.round(weight * factor) / factor).toFloat()
        } else {
            (Math.round((weight * 2.20F) * factor) / factor).toFloat()
        }
    }

    fun changeHeight(height: Float): BmiModel {
        return copy(height = height)
    }

    fun changeWeight(weight: Float): BmiModel {
        return copy(weight = weight)
    }

    fun changeUnit(measurementType: MeasurementType): BmiModel {
        return copy(measurementType = measurementType)
    }

}
