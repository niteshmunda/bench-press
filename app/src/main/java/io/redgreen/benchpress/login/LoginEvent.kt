package io.redgreen.benchpress.login

sealed class LoginEvent

data class EmailChangedEvent(
  val email: String
) : LoginEvent()

data class PasswordChangedEvent(
  val password: String
) : LoginEvent()
