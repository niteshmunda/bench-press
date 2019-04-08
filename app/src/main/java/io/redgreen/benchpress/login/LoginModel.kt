package io.redgreen.benchpress.login

import io.redgreen.benchpress.login.domain.Email
import io.redgreen.benchpress.login.domain.Password

data class LoginModel(
  val email: Email,
  val password: Password
) {
  companion object {
    val BLANK = LoginModel(Email(""), Password(""))
  }

  fun emailChanged(email: String): LoginModel {
    return copy(email = Email(email))
  }

  fun passwordChanged(password: String): LoginModel {
    return copy(password = Password(password))
  }
}
