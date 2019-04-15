package io.redgreen.benchpress.github.http

import io.reactivex.Single
import io.redgreen.benchpress.github.domain.User
import retrofit2.http.GET
import retrofit2.http.Path

interface GitHubApi {
    @GET("users/{username}/followers")
    fun getFollowers(
        @Path("username") username: String
    ): Single<List<User>>
}
