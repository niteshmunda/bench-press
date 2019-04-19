package io.redgreen.benchpress.login.db

import io.reactivex.Single

interface Repository {
    fun saveLoginResponse()
    fun saveToken(token :  String) : Single<Boolean>
}