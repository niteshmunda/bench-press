package io.redgreen.benchpress.login.db

import timber.log.Timber

class RepositoryImpl() : Repository {
    override fun saveLoginResponse() {

    }

    override fun saveToken() {
        Timber.i("Saving token to database")
        // dummy class for example.
    }
}