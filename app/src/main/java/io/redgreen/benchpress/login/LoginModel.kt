package io.redgreen.benchpress.login

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class LoginModel(
    val email : String,
    val password : String,
    val apiState : ApiState
) : Parcelable {
    companion object {
        val EMPTY = LoginModel("","",ApiState.IDLE)
    }

    constructor(email: String, password: String) : this(email, password, ApiState.IDLE)

    private fun verifyLogin(email: String, password: String): Boolean {
        if (isValidEmail(email) && isValidPassword(password))
            return true

        return false
    }

    private fun isValidPassword(password: String): Boolean {
        if (password.length >= 8) return true

        return false
    }

    private fun isValidEmail(email: String) : Boolean {
        if (email.isNotBlank() && email.contains("@") && email.contains(".")
            && email.indexOf("@") < email.indexOf(".")) return true

        return false
    }

    fun inputEmail(email: String) : LoginModel {
        return copy(email = email, apiState = ApiState.IDLE)
    }

    fun inputPassword(password: String) : LoginModel {
        return copy(password = password, apiState = ApiState.IDLE)
    }

    fun attemptLogin(): LoginModel {
        return copy(email = email, password = password, apiState = ApiState.LOADING)
    }

    fun loginSuccess(): LoginModel {
        return copy(email = email, password = password, apiState = ApiState.SUCCESS)
    }

    fun authError(): LoginModel {
        return copy(email = "", password = "", apiState = ApiState.IDLE)
    }

    fun apiError(): LoginModel {
        return copy(apiState = ApiState.FAIL)
    }
    val canLogin : Boolean
        get() = verifyLogin(email,password)

    val showEmailError: Boolean
        get() = !(email.contains("@") && email.contains(".")) && email.indexOf("@")  > email.indexOf(".")

    val showPasswordError: Boolean
        get() = !(password.length >= 8)

}
