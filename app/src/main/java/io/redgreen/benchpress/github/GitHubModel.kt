package io.redgreen.benchpress.github

import io.redgreen.benchpress.architecture.AsyncOp
import io.redgreen.benchpress.architecture.AsyncOp.IDLE
import io.redgreen.benchpress.architecture.AsyncOp.IN_FLIGHT

data class GitHubModel(
    val username: String,
    val fetchFollowersAsyncOp: AsyncOp
) {
    companion object {
        val EMPTY = GitHubModel("", IDLE)
    }

    fun usernameChanged(username: String): GitHubModel =
        copy(username = username)

    fun fetchingFollowers(): GitHubModel =
        copy(fetchFollowersAsyncOp = IN_FLIGHT)
}
