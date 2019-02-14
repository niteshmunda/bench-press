package io.redgreen.benchpress.login

import io.redgreen.benchpress.R

/**
 * Rendering logic for login page is encompassed here.
 */
class LoginViewRenderer(
    private val loginView: LoginView
) {

    fun render(model: LoginModel) {

        if (model.isReadyForLogin) {
            loginView.enableLogin()
        } else {
            loginView.disableLogin()
        }

        if (model.showEmailError) {
            loginView.showEmailError(R.string.invalid_email)
        } else {
            loginView.hideEmailError()
        }

        if (model.showPasswordError) {
            loginView.showEmailError(R.string.invalid_password)
        } else {
            loginView.hidePasswordError()
        }

        if (model.apiState == ApiState.LOADING) {
            loginView.showProgressbar()
        } else {
            loginView.hideProgressbar()
        }
    }

}