package io.redgreen.benchpress.github

sealed class GitHubEvent

data class UsernameChangedEvent(
    val username: String
) : GitHubEvent()

object UsernameClearedEvent : GitHubEvent()

data class FetchFollowersEvent(
    val username: String
) : GitHubEvent()