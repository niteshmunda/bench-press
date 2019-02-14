package io.redgreen.benchpress.login

import android.support.annotation.StringRes

/**
 * Documents all the methods which are there in login page.
 */
interface LoginView {

    fun enableLogin()
    fun disableLogin()
    fun showEmailError(@StringRes error: Int)
    fun showPasswordError(@StringRes error: Int)
    fun hideEmailError()
    fun hidePasswordError()
    fun showProgressbar()
    fun hideProgressbar()

}