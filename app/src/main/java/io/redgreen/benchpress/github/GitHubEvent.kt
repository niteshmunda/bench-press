package io.redgreen.benchpress.github

import io.redgreen.benchpress.github.domain.User

sealed class GitHubEvent

data class UsernameChangedEvent(
    val username: String
) : GitHubEvent()

object UsernameClearedEvent : GitHubEvent()

object RetryFetchFollowersEvent : GitHubEvent()

object FetchFollowersEvent : GitHubEvent()

data class FollowersFetchedEvent(
    val followers: List<User>
) : GitHubEvent()

object NoFollowersEvent : GitHubEvent()

object FollowersFetchFailedEvent : GitHubEvent()

object UsernameNotFoundEvent : GitHubEvent()
