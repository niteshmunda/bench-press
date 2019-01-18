package io.redgreen.benchpress.login

data class LoginModel(
    val email: String,
    val password: String
) {
    companion object {
        val EMPTY = LoginModel("","")
    }

    val isReadyForLogin: Boolean
        get() = false

    fun inputEmail(email: String): LoginModel {
        return copy(email = email)
    }

    fun inputPassword(password: String): LoginModel {
        return copy(password=password)
    }
}
