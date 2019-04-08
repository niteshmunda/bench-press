package io.redgreen.benchpress.login

sealed class LoginEvent

data class EmailChangedEvent(
  val email: String
) : LoginEvent()
