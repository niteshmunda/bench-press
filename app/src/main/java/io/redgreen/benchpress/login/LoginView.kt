package io.redgreen.benchpress.login

import android.support.annotation.StringRes

interface LoginView {
    fun enableLogin()
    fun disableLogin()
    fun showEmailError(@StringRes error : Int)
    fun showPasswordError(@StringRes error : Int)
    fun hideEmailError()
    fun hidePasswordError()
    fun showProgressBar()
    fun hideProgressBar()
}