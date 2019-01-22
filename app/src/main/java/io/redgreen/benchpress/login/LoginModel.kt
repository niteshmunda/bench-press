package io.redgreen.benchpress.login

import android.support.annotation.VisibleForTesting

data class LoginModel(
    val email: String,
    val password: String
) {
    companion object {
        val EMPTY = LoginModel("","")
    }

    val isReadyForLogin: Boolean
        get() = isValidLogin()

    val isValidEmail: Boolean
        get() = validEmail()

    val isValidPassword: Boolean
        get() = validPassword()

    fun inputEmail(email: String): LoginModel {
        return copy(email = email)
    }

    fun inputPassword(password: String): LoginModel {
        return copy(password=password)
    }

    @VisibleForTesting
    private fun isValidLogin(): Boolean {
        return (validEmail()
                && validPassword())
    }

    @VisibleForTesting
    private fun validEmail() = (email.isNotBlank() && email.contains("@") && email.contains(".")
            && email.indexOf("@") < email.lastIndexOf("."))

    @VisibleForTesting
    private fun validPassword() = password.isNotBlank() && password.length >= 8
}
