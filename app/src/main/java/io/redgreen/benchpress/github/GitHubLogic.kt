package io.redgreen.benchpress.github

import com.spotify.mobius.Next
import com.spotify.mobius.Next.next
import com.spotify.mobius.Update

object GitHubLogic : Update<GitHubModel, GitHubEvent, GitHubEffect> {
    override fun update(
        model: GitHubModel,
        event: GitHubEvent
    ): Next<GitHubModel, GitHubEffect> {
        return when (event) {
            is UsernameChangedEvent -> next(GitHubModel.EMPTY.usernameChanged(event.username))
            is UsernameClearedEvent -> next(GitHubModel.EMPTY)
            is FetchFollowersEvent -> next(model.fetchingFollowers(), setOf(FetchFollowersEffect(model.username)))
            is FollowersFetchedEvent -> next(model.followersFetchedSuccess(event.followers))
            is NoFollowersEvent -> next(model.noFollowers())
            is FollowersFetchFailedEvent -> next(model.followersFetchFailed())
            is UsernameNotFoundEvent -> next(model.usernameNotFound())
            is RetryFetchFollowersEvent -> next(model.fetchingFollowers(), setOf(FetchFollowersEffect((model.username))))
            else -> TODO("Unsupported event $event")
        }
    }
}
