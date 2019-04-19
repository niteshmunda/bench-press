package io.redgreen.benchpress.login

import io.redgreen.benchpress.R

class LoginViewRenderer(
    private val loginView : LoginView
) {
    fun render(model : LoginModel) {
        if (model.canLogin){
            loginView.enableLogin()
        }else{
            loginView.disableLogin()
        }
        if (model.showEmailError){
            loginView.showEmailError(R.string.invalid_email)
        }else{
            loginView.hideEmailError()
        }
        if (model.showPasswordError){
            loginView.showPasswordError(R.string.invalid_password)
        }
        else{
            loginView.hidePasswordError()
        }
        if (model.apiState == ApiState.LOADING){
            loginView.showProgressBar()
        }else{
            loginView.hideProgressBar()
        }
    }
}