package io.redgreen.benchpress.bmi

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize
import java.nio.channels.FileLock

@Parcelize
data class BmiModel(
    var height: Float,
    var weight: Float,
    var bmi: Float,
    var measurementType: MeasurementType,
    var weightCategory: WeightCategory? = null
) : Parcelable {
    companion object {
        val ZERO = BmiModel(
            height = 0F,
            weight = 0F,
            bmi = 0F,
            measurementType = MeasurementType.METRIC,
            weightCategory = null
        )

        val DEFAULT = BmiModel(
            height = 182.50F,
            weight = 80.0F,
            bmi = 24.0F,
            measurementType = MeasurementType.METRIC,
            weightCategory = WeightCategory.NORMAL_WEIGHT
        )
    }

    fun calculateBMI(height : Float,weight : Float, measurementType : MeasurementType) : Float{

        var bmi = if (measurementType == MeasurementType.METRIC) {
            ((weight / (height * height) * 10000))
        } else {
            ((weight / (height * height) * 10000)) * 703
        }
        val factor = Math.pow(10.0, 2.0)
        bmi = (Math.round(bmi * factor) / factor).toFloat()

        weightCategory = calculateWeightCatagory(bmi)
        return bmi
    }

    fun calculateWeightCatagory(bmi : Float) : WeightCategory{
        var weightCategory = when {
            bmi <= 18.5 -> WeightCategory.UNDERWEIGHT
            bmi > 18.5 && bmi <= 25 -> WeightCategory.NORMAL_WEIGHT
            bmi > 25 && bmi < 30 -> WeightCategory.OVER_WEIGHT
            else -> WeightCategory.OBESE
        }
        return weightCategory
    }

    fun changeHeightUnit(measurementType: MeasurementType) : Float{
       if (measurementType == MeasurementType.IMPERIAL){
            val factor = 2.54F
           height /= factor
        }
        else {
            val factor = 2.54F
            height *= factor
       }

        val factor = Math.pow(10.0, 2.0)
        height = (Math.round(height * factor) / factor).toFloat()
        return this.height
    }
    fun changeWeightUnit(measurementType: MeasurementType) : Float{
        if (measurementType == MeasurementType.IMPERIAL){
            val factor = 2.205F
            weight *= factor
        }
        else{
            weight /= 2.205F
        }
        val factor = Math.pow(10.0, 2.0)
        weight = (Math.round(weight * factor) / factor).toFloat()
        return this.weight
    }
    fun heightChange(height: Float): BmiModel {
        return copy(height = height,bmi = calculateBMI(height,weight,measurementType))
    }

    fun weightChange(weight: Float): BmiModel {
        return copy(weight = weight,bmi = calculateBMI(height,weight,measurementType))
    }

    fun unitChange(measurementType: MeasurementType): BmiModel {
        return copy(measurementType = measurementType,height = changeHeightUnit(measurementType), weight = changeWeightUnit(measurementType))
    }
}

