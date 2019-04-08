package io.redgreen.benchpress.login

import io.redgreen.benchpress.login.domain.AsyncOp
import io.redgreen.benchpress.login.domain.AsyncOp.*
import io.redgreen.benchpress.login.domain.Email
import io.redgreen.benchpress.login.domain.LoginError
import io.redgreen.benchpress.login.domain.LoginError.*
import io.redgreen.benchpress.login.domain.Password

data class LoginModel(
  val email: Email,
  val password: Password,
  val loggingInAsyncOp: AsyncOp = IDLE,
  val loginError: LoginError = NONE
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
    return copy(loggingInAsyncOp = IN_PROGRESS, loginError = NONE)
  }

  fun userAuthenticated(): LoginModel {
    return copy(loggingInAsyncOp = SUCCEEDED)
  }

  fun userAuthenticationFailed(): LoginModel {
    return copy(loggingInAsyncOp = FAILED, loginError = AUTHENTICATION)
  }

  fun unknownFailure(): LoginModel {
    return copy(loggingInAsyncOp = FAILED, loginError = UNKNOWN)
  }
}
