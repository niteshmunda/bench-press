package io.redgreen.benchpress.bmi

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class BmiModel(
    var height: Int,
    var weight: Int,
    var bmi: Double,
    var measurementType: MeasurementType,
    var weightCategory: WeightCategory
) : Parcelable {
    companion object {
        val DEFAULT = BmiModel(
            height = 176,
            weight = 74,
            bmi = 12.3,//// TODO(ns) 25/Feb/19 - fix this
            measurementType = MeasurementType.METRIC,
            weightCategory = WeightCategory.NORMAL_WEIGHT
        )
    }

    fun calculateBMI() {
        bmi = if (measurementType == MeasurementType.METRIC) {
            ((weight / (height * height) * 10000).toDouble())
        } else {
            ((weight / (height * height) * 10000).toDouble()) * 703
        }
    }


    fun heightChange(height: Int) {
        copy(height = height)
        calculateBMI()
    }

    fun weightChange(weight: Int) {
        copy(weight = weight)
        calculateBMI()
    }

    fun unitChange(measurementType: MeasurementType): BmiModel =
        copy(measurementType = measurementType)

}

