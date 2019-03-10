package io.redgreen.benchpress.bmi

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

// 1. Clean up primitive obsession on weight and height. (Thereby resolving the precision loss problem that we have now.)
// 2. Use the factory function to set the initial state.

@Parcelize
data class BmiModel(
    val height: Float,
    val weight: Float,
    val measurementType: MeasurementType
) : Parcelable {
    companion object {
        fun modelFor(
            weight: Float,
            height: Float
        ): BmiModel = BmiModel(
            height = height,
            weight = weight,
            measurementType = MeasurementType.METRIC
        )

//        @Deprecated("Replaced with a factory function.")
//        val DEFAULT = BmiModel(
//            height = 160.0F,
//            weight = 48.0F,
//            measurementType = MeasurementType.METRIC
//        )
    }

    val bmi: Float
        get() {
            val bmi = ((weight / (height * height) * 10000))
            val factor = Math.pow(10.0, 2.0)
            return (Math.round(bmi * factor) / factor).toFloat()
        }

    val weightCategory: WeightCategory
        get() {
            val computedBmi = bmi
            return when {
                computedBmi <= 18.5 -> WeightCategory.UNDERWEIGHT
                computedBmi > 18.5 && computedBmi <= 25 -> WeightCategory.NORMAL_WEIGHT
                computedBmi > 25 && computedBmi < 30 -> WeightCategory.OVER_WEIGHT
                else -> WeightCategory.OBESE
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

    fun changeHeight(height: Float): BmiModel =
        copy(height = height)

    fun changeWeight(weight: Float): BmiModel =
        copy(weight = weight)

    fun changeUnit(measurementType: MeasurementType): BmiModel =
        copy(measurementType = measurementType)
}
