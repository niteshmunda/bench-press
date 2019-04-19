package io.redgreen.benchpress.github

import android.os.Parcelable
import io.redgreen.benchpress.architecture.AsyncOp
import io.redgreen.benchpress.architecture.AsyncOp.*
import io.redgreen.benchpress.github.GitHubModel.UsernamePresence.*
import io.redgreen.benchpress.github.domain.User
import kotlinx.android.parcel.Parcelize

@Parcelize
data class GitHubModel(
    val username: String,
    val fetchFollowersAsyncOp: AsyncOp = IDLE,
    val followers: List<User> = emptyList(),
    val usernamePresence: UsernamePresence = UNKNOWN
) : Parcelable {
    val canSearch: Boolean
        get() {
            return username.isNotBlank()
        }

    val hasFollowers: Boolean
        get() {
            return followers.isNotEmpty()
        }

    companion object {
        val EMPTY = GitHubModel("", IDLE, emptyList())
    }

    fun usernameChanged(username: String): GitHubModel =
        copy(username = username.trim(), usernamePresence = UNKNOWN)

    fun fetchingFollowers(): GitHubModel =
        copy(fetchFollowersAsyncOp = IN_FLIGHT)

    fun followersFetchedSuccess(followers: List<User>): GitHubModel =
        copy(fetchFollowersAsyncOp = SUCCEEDED, followers = followers)

    fun noFollowers(): GitHubModel =
        copy(fetchFollowersAsyncOp = SUCCEEDED, followers = emptyList(), usernamePresence = FOUND)

    fun followersFetchFailed(): GitHubModel =
        copy(fetchFollowersAsyncOp = FAILED, followers = emptyList(), usernamePresence = UNKNOWN)

    fun usernameNotFound(): GitHubModel =
        copy(fetchFollowersAsyncOp = SUCCEEDED, followers = emptyList(), usernamePresence = NOT_FOUND)

    enum class UsernamePresence {
        UNKNOWN, FOUND, NOT_FOUND
    }
}
