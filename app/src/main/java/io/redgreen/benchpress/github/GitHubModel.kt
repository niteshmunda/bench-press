package io.redgreen.benchpress.github

import io.redgreen.benchpress.architecture.AsyncOp
import io.redgreen.benchpress.architecture.AsyncOp.IDLE
import io.redgreen.benchpress.architecture.AsyncOp.IN_FLIGHT
import io.redgreen.benchpress.github.domain.User

data class GitHubModel(
    val username: String,
    val fetchFollowersAsyncOp: AsyncOp = IDLE,
    val followers: List<User> = emptyList()
) {
    companion object {
        val EMPTY = GitHubModel("")
    }

    fun usernameChanged(username: String): GitHubModel =
        copy(username = username)

    fun fetchingFollowers(): GitHubModel =
        copy(fetchFollowersAsyncOp = IN_FLIGHT)

    fun followersFetched(followers: List<User>): GitHubModel =
        copy(/*fetchFollowersAsyncOp = SUCCEEDED,*/ followers = followers)
}
