package io.redgreen.benchpress.github

class GitHubViewRenderer(private val view: GitHubView) {
    fun render(model: GitHubModel) {
        view.disableSearchButton()
        view.hideFollowers()
        view.enableUsernameTextView()
        view.hideNoFollowersMessage()
        view.hideRetryMessage()
        view.hideUsernameNotFoundMessage()
    }
}
