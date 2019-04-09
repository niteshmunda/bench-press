package io.redgreen.benchpress.github.http

import io.reactivex.Single
import io.redgreen.benchpress.github.domain.User

interface GitHubApi {
    fun getFollowers(username: String): Single<List<User>>
}
