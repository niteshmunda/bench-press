package io.redgreen.benchpress.github

import io.redgreen.benchpress.architecture.AsyncOp
import io.redgreen.benchpress.architecture.AsyncOp.*
import io.redgreen.benchpress.github.domain.User

data class GitHubModel(
    val username: String,
    val fetchFollowersAsyncOp: AsyncOp = IDLE,
    val followers: List<User> = emptyList(),
    val usernameNotFound: Boolean = false /* Deal with when the username is found! NOW. */
) {
    companion object {
        val EMPTY = GitHubModel("", IDLE, emptyList(), false)
    }

    fun usernameChanged(username: String): GitHubModel =
        copy(username = username)

    fun fetchingFollowers(): GitHubModel =
        copy(fetchFollowersAsyncOp = IN_FLIGHT)

    fun followersFetchedSuccess(followers: List<User>): GitHubModel =
        copy(/*fetchFollowersAsyncOp = SUCCEEDED,*/ followers = followers)

    fun noFollowers(): GitHubModel =
        copy(fetchFollowersAsyncOp = SUCCEEDED, followers = emptyList())

    fun followersFetchFailed(): GitHubModel =
        copy(fetchFollowersAsyncOp = FAILED, followers = emptyList())

    fun usernameNotFound(): GitHubModel =
        copy(fetchFollowersAsyncOp = SUCCEEDED, followers = emptyList(), usernameNotFound = true)
}
