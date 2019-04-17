package io.redgreen.benchpress.github

import io.redgreen.benchpress.architecture.AsyncOp
import io.redgreen.benchpress.github.domain.User

class GitHubViewRenderer(private val view: GitHubView) {
    fun render(model: GitHubModel) {
        if (model.fetchFollowersAsyncOp == AsyncOp.SUCCEEDED && model.usernamePresence == GitHubModel.UsernamePresence.NOT_FOUND) {
            showUserNotFound()
        } else if (model.fetchFollowersAsyncOp == AsyncOp.FAILED) {
            showUnknownError()
        } else if (model.fetchFollowersAsyncOp == AsyncOp.SUCCEEDED && model.hasFollowers) {
            showFollowers(model.followers)
        } else if (model.fetchFollowersAsyncOp == AsyncOp.SUCCEEDED && !model.hasFollowers) {
            showNoFollowersFound()
        } else if (model.fetchFollowersAsyncOp == AsyncOp.IN_FLIGHT) {
            showLoading()
        } else if (model.canSearch && model.fetchFollowersAsyncOp == AsyncOp.IDLE) {
            showReadyToFetchFollowers()
        } else if (!model.canSearch && model.fetchFollowersAsyncOp == AsyncOp.IDLE) {
            showEmpty()
        }
    }

    private fun showEmpty() {
        with(view) {
            disableSearchButton()
            hideFollowers()
            enableUsernameTextView()
            hideNoFollowersMessage()
            hideRetryMessage()
            hideUsernameNotFoundMessage()
            showWelcomeMessage()
        }
    }

    private fun showReadyToFetchFollowers() {
        with(view) {
            enableSearchButton()
            showWelcomeMessage()
            hideProgress()
            hideRetryMessage()
            hideFollowers()
            hideUsernameNotFoundMessage()
            hideNoFollowersMessage()
        }
    }

    private fun showLoading() {
        with(view) {
            disableSearchButton()
            disableUsernameTextView()
            showProgress()
            hideRetryMessage()
            hideWelcomeMessage()
        }
    }

    private fun showFollowers(
        followers: List<User>
    ) {
        with(view) {
            enableUsernameTextView()
            hideProgress()
            showFollowers(followers)
            enableSearchButton()
        }
    }

    private fun showUserNotFound() {
        with(view) {
            enableSearchButton()
            enableUsernameTextView()
            hideProgress()
            showUsernameNotFoundMessage()
        }
    }

    private fun showNoFollowersFound() {
        with(view) {
            enableUsernameTextView()
            enableSearchButton()
            hideProgress()
            showNoFollowersMessage()
        }
    }

    private fun showUnknownError() {
        with(view) {
            enableUsernameTextView()
            enableSearchButton()
            hideProgress()
            showRetryMessage()
        }
    }
}
