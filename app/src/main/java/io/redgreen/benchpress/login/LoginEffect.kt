package io.redgreen.benchpress.login

sealed class LoginEffect

object AttemptLoginEffect : LoginEffect()
