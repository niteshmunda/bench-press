package io.redgreen.benchpress.login.domain

data class Password(val secret: String) {
  fun isValid(): Boolean {
    return secret.length >= 8
  }
}
