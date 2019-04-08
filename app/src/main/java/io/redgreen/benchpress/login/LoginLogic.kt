package io.redgreen.benchpress.login

import com.spotify.mobius.Next
import com.spotify.mobius.Next.next
import com.spotify.mobius.Update

object LoginLogic : Update<LoginModel, LoginEvent, LoginEffect> {
  override fun update(
    model: LoginModel,
    event: LoginEvent
  ): Next<LoginModel, LoginEffect> {
    return when (event) {
      is EmailChangedEvent -> next(model.emailChanged(event.email))
      is PasswordChangedEvent -> next(model.passwordChanged(event.password))
      is AttemptLoginEvent -> next(model.attemptLogin(), setOf(AttemptLoginEffect))
      is UserAuthenticatedEvent -> next(model.userAuthenticated(), setOf(GoToHomeEffect(event.token)))
      is UserAuthenticationFailEvent -> next(model.userAuthenticationFailed())
      is UnknownFailureEvent -> next(model.unknownFailure())
    }
  }
}
