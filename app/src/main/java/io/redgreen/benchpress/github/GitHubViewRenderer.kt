package io.redgreen.benchpress.github

import io.redgreen.benchpress.architecture.AsyncOp

class GitHubViewRenderer(private val view: GitHubView) {
    fun render(model: GitHubModel) {

        if (model.fetchFollowersAsyncOp == AsyncOp.SUCCEEDED && model.usernamePresence == GitHubModel.UsernamePresence.NOT_FOUND) {
            view.enableSearchButton()
            view.enableUsernameTextView()
            view.hideProgress()
            view.showUsernameNotFoundMessage()
        }

        else if (model.fetchFollowersAsyncOp == AsyncOp.FAILED) {
            view.enableUsernameTextView()
            view.enableSearchButton()
            view.hideProgress()
            view.showRetryMessage()
        }

        else if (model.fetchFollowersAsyncOp == AsyncOp.SUCCEEDED && model.hasFollowers) {
            view.enableUsernameTextView()
            view.hideProgress()
            view.showFollowers()
            view.enableSearchButton()
        }

        else if (model.fetchFollowersAsyncOp == AsyncOp.SUCCEEDED && !model.hasFollowers) {
            view.enableUsernameTextView()
            view.enableSearchButton()
            view.hideProgress()
            view.showNoFollowersMessage()
        }

        else if (model.fetchFollowersAsyncOp == AsyncOp.IN_FLIGHT) {
            view.disableSearchButton()
            view.disableUsernameTextView()
            view.showProgress()
            view.hideRetryMessage()
        }

        else if (model.canSearch && model.fetchFollowersAsyncOp == AsyncOp.IDLE) {
            view.enableSearchButton()
        }

        else if (!model.canSearch && model.fetchFollowersAsyncOp == AsyncOp.IDLE) {
            view.disableSearchButton()
            view.hideFollowers()
            view.enableUsernameTextView()
            view.hideNoFollowersMessage()
            view.hideRetryMessage()
            view.hideUsernameNotFoundMessage()
        }
    }
}
