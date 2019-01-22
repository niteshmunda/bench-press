package io.redgreen.benchpress.login

import android.support.annotation.VisibleForTesting
import io.redgreen.benchpress.login.ApiState.*

data class LoginModel(
    val email: String,
    val password: String,
    val apiState: ApiState
) {
    companion object {
        val EMPTY = LoginModel("", "", IDLE)
    }

    // Assigns idle state by default.
    constructor(email: String, password: String) : this(email, password, IDLE)

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
        return copy(password = password)
    }

    fun apiCalled(): LoginModel {
        return copy(email = email, password = password, apiState = LOADING)
    }

    fun apiSuccessful(): LoginModel {
        return copy(email = email, password = password, apiState = SUCCESS)
    }

    fun apiError(): LoginModel {
        return copy(email = email, password = password, apiState = FAIL)
    }

    fun authError(): LoginModel {
        return copy(email = "", password = "", apiState = IDLE)
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
