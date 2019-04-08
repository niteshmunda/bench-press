package io.redgreen.benchpress.login

import com.spotify.mobius.Next
import com.spotify.mobius.Update

object LoginLogic : Update<LoginModel, LoginEvent, LoginEffect> {
  override fun update(
    model: LoginModel,
    event: LoginEvent
  ): Next<LoginModel, LoginEffect> {
    if (event is EmailChangedEvent) {
      return Next.next(model.emailChanged(event.email))
    }

    throw UnsupportedOperationException("Unknown event: $event")
  }
}
