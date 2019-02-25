package io.redgreen.benchpress.bmi

import com.spotify.mobius.Next
import com.spotify.mobius.Next.next
import com.spotify.mobius.Update

object BmiLogic : Update<BmiModel, BmiLogic, Nothing> {
    override fun update(
        model: BmiModel,
        event: BmiLogic
    ): Next<BmiModel, Nothing> {
        return when (event) {
            is HeightChangeEvent -> next(model.heightChange(event.height))
            is WeightChangeEvent -> next(model.weightChange(event.weight))
            is UnitChangeEvent -> next(model.unitChange())
        }
    }
}
