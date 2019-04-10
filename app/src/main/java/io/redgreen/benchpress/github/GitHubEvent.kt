package io.redgreen.benchpress.github

import io.redgreen.benchpress.github.domain.User

sealed class GitHubEvent

data class UsernameChangedEvent(
    val username: String
) : GitHubEvent()

object UsernameClearedEvent : GitHubEvent()

object RetryFetchFollowersEvent : GitHubEvent()

data class FetchFollowersEvent(
    val username: String // FIXME This is redundant, this doesn't have to be passed externally.
) : GitHubEvent()

data class FollowersFetchedEvent(
    val followers: List<User>
) : GitHubEvent()

object NoFollowersEvent : GitHubEvent()

object FollowersFetchFailedEvent : GitHubEvent()

object UsernameNotFoundEvent : GitHubEvent()
