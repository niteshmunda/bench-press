package io.redgreen.benchpress.login.api

import io.reactivex.Single
import io.redgreen.benchpress.login.ApiService
import io.redgreen.benchpress.login.LoginRequest
import io.redgreen.benchpress.login.LoginResponse
import java.util.concurrent.TimeUnit

class ApiServiceImpl : ApiService {

    override fun login(request: LoginRequest): Single<LoginResponse> {
        return if (System.currentTimeMillis() % 2 == 0L) {
            Single
                .just(LoginResponse("token"))
                .delay(300, TimeUnit.MILLISECONDS)
        } else {
            Single.error {
                throw Error("Timeout")
            }
        }
    }
}