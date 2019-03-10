package io.redgreen.benchpress.login

import io.reactivex.Single

interface ApiService {
    fun login(request: LoginRequest) : Single<LoginResponse>
}