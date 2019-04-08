package io.redgreen.benchpress.login

sealed class LoginEvent

data class EmailChangedEvent(
  val email: String
) : LoginEvent()

data class PasswordChangedEvent(
  val password: String
) : LoginEvent()

object AttemptLoginEvent : LoginEvent()

data class UserAuthenticatedEvent(
  val token: String
) : LoginEvent()

object UserAuthenticationFailEvent : LoginEvent()

