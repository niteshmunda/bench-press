package io.redgreen.benchpress.login

import io.redgreen.benchpress.login.domain.AsyncOp
import io.redgreen.benchpress.login.domain.AsyncOp.*
import io.redgreen.benchpress.login.domain.Email
import io.redgreen.benchpress.login.domain.Password

data class LoginModel(
  val email: Email,
  val password: Password,
  val loggingInAsyncOp: AsyncOp = IDLE
) {
  companion object {
    val BLANK = LoginModel(Email(""), Password(""))
  }

  val isReadyForLogin: Boolean
    get() = email.isValid() && password.isValid()

  fun emailChanged(email: String): LoginModel {
    return copy(email = Email(email))
  }

  fun passwordChanged(password: String): LoginModel {
    return copy(password = Password(password))
  }

  fun attemptLogin(): LoginModel {
    return copy(loggingInAsyncOp = IN_PROGRESS)
  }

  fun userAuthenticated(): LoginModel {
    return copy(loggingInAsyncOp = SUCCEEDED)
  }
}
