package io.redgreen.benchpress.bmi

sealed class BmiEvent

data class HeightChangeEvent(val height : Double) : BmiEvent()

data class WeightChangeEvent(val weight : Int) : BmiEvent()

object UnitChangeEvent : BmiEvent()