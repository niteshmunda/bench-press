package io.redgreen.benchpress.counter

import com.spotify.mobius.Next
import com.spotify.mobius.Next.next
import com.spotify.mobius.Update

object CounterLogic : Update<CounterModel, CounterEvent, Nothing> {
    override fun update(
        model: CounterModel,
        event: CounterEvent
    ): Next<CounterModel, Nothing> { // 0/1 (Model update) - * (Effects)
        return if (event is IncrementEvent) {
            next(model.increment())
        } else {
            next(model.decrement())
        }
    }
}
