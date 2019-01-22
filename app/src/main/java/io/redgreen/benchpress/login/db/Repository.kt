package io.redgreen.benchpress.login.db

interface Repository {

    fun saveLoginResponse()
    fun saveToken()
}
