package io.redgreen.benchpress.login.db

import io.reactivex.Single
import timber.log.Timber
import java.util.concurrent.TimeUnit

class RepositoryImpl : Repository {

    override fun saveLoginResponse() {

    }

    override fun saveToken(token : String) : Single<Boolean> {
        Timber.i("Saving token to database")
        return Single
            .just(true)
            .delay(30, TimeUnit.MILLISECONDS)
    }
}