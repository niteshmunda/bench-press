package io.redgreen.benchpress.login

sealed class LoginEvent

data class InputEmailEvent(val email : String) : LoginEvent()
data class InputPasswordEvent(val password : String) : LoginEvent()
object AttemptLoginEvent : LoginEvent()
data class LoginSuccessEvent(val response : LoginResponse) : LoginEvent()
data class LoginAuthFailedEvent(val error : NetworkError) : LoginEvent()
data class LoginTimeoutFailedEvent (val error : NetworkError) : LoginEvent()
object TokenSavedEvent : LoginEvent()
data class RetryEvent (val token : String) : LoginEvent()