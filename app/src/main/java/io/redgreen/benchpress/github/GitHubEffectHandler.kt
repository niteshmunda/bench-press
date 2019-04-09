package io.redgreen.benchpress.github

import com.spotify.mobius.rx2.RxMobius
import io.reactivex.Observable
import io.reactivex.ObservableSource
import io.reactivex.ObservableTransformer
import io.redgreen.benchpress.github.http.GitHubApi

object GitHubEffectHandler {
    fun createHandler(
        gitHubApi: GitHubApi
    ): ObservableTransformer<GitHubEffect, GitHubEvent> {
        return RxMobius
            .subtypeEffectHandler<GitHubEffect, GitHubEvent>()
            .addTransformer(FetchFollowersEffect::class.java, fetchFollowersTransformer(gitHubApi))
            .build()
    }

    private fun fetchFollowersTransformer(
        gitHubApi: GitHubApi
    ): ObservableTransformer<FetchFollowersEffect, GitHubEvent> {
        return object : ObservableTransformer<FetchFollowersEffect, GitHubEvent> {
            override fun apply(fetchFollowersEffects: Observable<FetchFollowersEffect>): ObservableSource<GitHubEvent> {
                return fetchFollowersEffects
                    .flatMapSingle { gitHubApi.getFollowers(it.username) }
                    .map { followers -> FollowersFetchedEvent(followers) }
            }
        }
    }
}
