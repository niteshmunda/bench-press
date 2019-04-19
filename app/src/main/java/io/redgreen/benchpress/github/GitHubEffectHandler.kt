package io.redgreen.benchpress.github

import com.spotify.mobius.rx2.RxMobius
import io.reactivex.Observable
import io.reactivex.ObservableSource
import io.reactivex.ObservableTransformer
import io.redgreen.benchpress.architecture.threading.SchedulersProvider
import io.redgreen.benchpress.github.http.GitHubApi
import retrofit2.HttpException

object GitHubEffectHandler {
    fun createHandler(
        gitHubApi: GitHubApi,
        schedulersProvider: SchedulersProvider
    ): ObservableTransformer<GitHubEffect, GitHubEvent> {
        return RxMobius
            .subtypeEffectHandler<GitHubEffect, GitHubEvent>()
            .addTransformer(FetchFollowersEffect::class.java, fetchFollowersTransformer(gitHubApi, schedulersProvider))
            .build()
    }

    private fun fetchFollowersTransformer(
        gitHubApi: GitHubApi,
        schedulersProvider: SchedulersProvider
    ): ObservableTransformer<FetchFollowersEffect, GitHubEvent> {
        return object : ObservableTransformer<FetchFollowersEffect, GitHubEvent> {
            override fun apply(fetchFollowersEffects: Observable<FetchFollowersEffect>): ObservableSource<GitHubEvent> {
                return fetchFollowersEffects
                    .flatMapSingle {
                        gitHubApi
                            .getFollowers(it.username)
                            .subscribeOn(schedulersProvider.io)
                            .map { followers -> if (followers.isEmpty()) NoFollowersEvent else FollowersFetchedEvent(followers) }
                            .onErrorReturn(::mapToDomainErrorEvent)
                    }
            }
        }
    }

    private fun mapToDomainErrorEvent(throwable: Throwable): GitHubEvent {
        return if (throwable is HttpException && isUsernameNotFoundException(throwable)) {
            UsernameNotFoundEvent
        } else {
            FollowersFetchFailedEvent
        }
    }

    private fun isUsernameNotFoundException(exception: HttpException): Boolean =
        exception.code() == 404
}
