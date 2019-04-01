package io.redgreen.benchpress.github

import io.redgreen.benchpress.github.domain.User

sealed class GitHubEvent

data class UsernameChangedEvent(
    val username: String
) : GitHubEvent()

object UsernameClearedEvent : GitHubEvent()

data class FetchFollowersEvent(
    val username: String
) : GitHubEvent()

data class FollowersFetchedEvent(
    val followers: List<User>
) : GitHubEvent()

object NoFollowersEvent : GitHubEvent()