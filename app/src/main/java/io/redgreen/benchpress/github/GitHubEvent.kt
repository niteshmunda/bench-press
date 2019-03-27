package io.redgreen.benchpress.github

sealed class GitHubEvent

data class UsernameChangedEvent(
    val username: String
) : GitHubEvent()
