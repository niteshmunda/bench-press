package io.redgreen.benchpress.hellostranger

import com.spotify.mobius.Next
import com.spotify.mobius.Update

object HelloStrangerLogic : Update<HelloStrangerModel, HelloStrangerEvent, Nothing> {
    override fun update(model: HelloStrangerModel, event: HelloStrangerEvent): Next<HelloStrangerModel, Nothing> {
        return Next.next(model.copy(name = if(event is EnterNameEvent) event.name else ""))
    }
}
