package io.redgreen.benchpress.bmi

enum class WeightCategory(val category: String) {
    UNDERWEIGHT("Under Weight"), // <!-- TODO How do we deal with localization?
    NORMAL_WEIGHT("Normal Weight"),
    OVER_WEIGHT("Over Weight"),
    OBESE("Obesity")
}
