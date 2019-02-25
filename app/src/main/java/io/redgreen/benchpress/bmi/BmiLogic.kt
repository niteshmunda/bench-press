package io.redgreen.benchpress.bmi

import com.spotify.mobius.Next
import com.spotify.mobius.Next.next
import com.spotify.mobius.Update

object BmiLogic : Update<BmiModel, BmiEvent, Nothing> {
    override fun update(
        model: BmiModel,
        event: BmiEvent
    ): Next<BmiModel, Nothing> {
        return when (event) {
            is HeightChangeEvent -> {
                next(model.heightChange(event.height))
            }
            is WeightChangeEvent -> {
                next(model.weightChange(event.weight))
            }
            is UnitChangeEvent -> {
                next(model.unitChange(event.measurementType))
            }
            else -> TODO()
        }
    }
}
