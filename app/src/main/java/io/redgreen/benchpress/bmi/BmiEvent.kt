package io.redgreen.benchpress.bmi

sealed class BmiEvent

data class HeightChangeEvent(val height : Float) : BmiEvent()

data class WeightChangeEvent(val weight : Float) : BmiEvent()

data class UnitChangeEvent(val measurementType: MeasurementType) : BmiEvent()
