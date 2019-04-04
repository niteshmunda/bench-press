package io.redgreen.benchpress.github

interface GitHubView {
    fun disableSearchButton()
    fun hideFollowers()
    fun enableUsernameTextView()
    fun hideNoFollowersMessage()
    fun hideRetryMessage()
    fun hideUsernameNotFoundMessage()
    fun enableSearchButton()
}
