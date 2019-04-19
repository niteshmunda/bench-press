package io.redgreen.benchpress.login.db

import io.reactivex.Single
import java.util.concurrent.TimeUnit

class RepositoryImpl : Repository {
    override fun saveLoginResponse() {

    }

    override fun saveToken(token: String): Single<Boolean> {
        return Single
            .just(true)
            .delay(30,TimeUnit.MILLISECONDS)
    }
}