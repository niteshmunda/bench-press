package io.redgreen.benchpress.login.domain

data class Email(val email: String) {
  fun isValid(): Boolean =
    email.indexOf('@') < email.lastIndexOf('.')
}
