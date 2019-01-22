package io.redgreen.benchpress.login

data class LoginModel(
    val email: String,
    val password: String
) {
    companion object {
        val EMPTY = LoginModel("","")
    }

    val isReadyForLogin: Boolean
        get() = isValidLogin()

    fun inputEmail(email: String): LoginModel {
        return copy(email = email)
    }

    fun inputPassword(password: String): LoginModel {
        return copy(password=password)
    }

    private fun isValidLogin(): Boolean {
        return (email.contains("@") && email.contains(".")
                && email.indexOf("@") < email.lastIndexOf(".")
                && password.length >= 8)
    }
}
