package io.redgreen.benchpress.github

import io.redgreen.benchpress.github.domain.User

interface GitHubView {
    fun disableSearchButton()
    fun hideFollowers()
    fun enableUsernameTextView()
    fun hideNoFollowersMessage()
    fun hideRetryMessage()
    fun hideUsernameNotFoundMessage()
    fun enableSearchButton()
    fun disableUsernameTextView()
    fun showProgress()
    fun showFollowers(followers: List<User>)
    fun hideProgress()
    fun showNoFollowersMessage()
    fun showRetryMessage()
    fun showUsernameNotFoundMessage()
    fun showWelcomeMessage()
    fun hideWelcomeMessage()
}
