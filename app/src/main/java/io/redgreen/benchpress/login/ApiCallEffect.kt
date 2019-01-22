package io.redgreen.benchpress.login

sealed class LoginEffect

data class ApiCallEffect(val request : LoginRequest) : LoginEffect()

object SaveTokenEffect : LoginEffect()
