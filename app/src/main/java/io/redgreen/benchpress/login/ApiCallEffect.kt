package io.redgreen.benchpress.login

sealed class LoginEffect

data class ApiCallEffect(val request : LoginRequest) : LoginEffect()
data class NavigateEffect(val to : NavigateTo) : LoginEffect()

data class SaveTokenEffect(val token : String) : LoginEffect()
object ClearFieldsEffect : LoginEffect()
object RetryEffect : LoginEffect()
