package io.redgreen.benchpress.login

interface LoginAction {
    fun navigateToHome()
    fun clearFields()
    fun retry()
}