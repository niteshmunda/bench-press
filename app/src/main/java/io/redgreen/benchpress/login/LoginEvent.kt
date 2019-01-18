package io.redgreen.benchpress.login




sealed class LoginEvent

data class InputEmailEvent(val email: String) : LoginEvent()
data class InputPasswordEvent(val password: String) : LoginEvent()


