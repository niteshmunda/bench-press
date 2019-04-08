package io.redgreen.benchpress.login

data class LoginModel(
  val email: String,
  val password: String
) {
  companion object {
    val BLANK = LoginModel("", "")
  }

  fun emailChanged(email: String): LoginModel {
    return copy(email = email)
  }

  fun passwordChanged(password: String): LoginModel {
    return copy(password = password)
  }
}
