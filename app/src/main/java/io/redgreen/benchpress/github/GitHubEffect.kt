package io.redgreen.benchpress.github

sealed class GitHubEffect

data class FetchFollowersEffect(
    val username: String
) : GitHubEffect()
