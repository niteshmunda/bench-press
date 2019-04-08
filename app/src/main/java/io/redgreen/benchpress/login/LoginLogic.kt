package io.redgreen.benchpress.login

import com.spotify.mobius.Next
import com.spotify.mobius.Update

object LoginLogic : Update<LoginModel, LoginEvent, LoginEffect> {
  override fun update(
    model: LoginModel,
    event: LoginEvent
  ): Next<LoginModel, LoginEffect> {
    return when (event) {
      is EmailChangedEvent -> Next.next(model.emailChanged(event.email))
      is PasswordChangedEvent -> Next.next(model.passwordChanged(event.password))
      is AttemptLoginEvent -> Next.next(model.attemptLogin(), setOf(AttemptLoginEffect))
      is UserAuthenticatedEvent -> Next.next(model.userAuthenticated(), setOf(GoToHomeEffect(event.token)))
      else -> TODO("Unknown event: $event")
    }
  }
}
