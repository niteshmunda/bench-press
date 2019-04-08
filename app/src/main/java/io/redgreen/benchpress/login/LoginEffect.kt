package io.redgreen.benchpress.login

sealed class LoginEffect

object AttemptLoginEffect : LoginEffect()

data class GoToHomeEffect(
  val token: String
) : LoginEffect()
