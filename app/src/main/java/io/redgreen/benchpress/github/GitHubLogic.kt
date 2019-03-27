package io.redgreen.benchpress.github

import com.spotify.mobius.Next
import com.spotify.mobius.Update

object GitHubLogic : Update<GitHubModel, GitHubEvent, GitHubEffect> {
    override fun update(
        model: GitHubModel,
        event: GitHubEvent
    ): Next<GitHubModel, GitHubEffect> {
        if (event is UsernameChangedEvent) {
            return Next.next(model.usernameChanged(event.username))
        }
        TODO()
    }
}
