package io.redgreen.benchpress.github

import com.spotify.mobius.test.NextMatchers.*
import com.spotify.mobius.test.UpdateSpec
import com.spotify.mobius.test.UpdateSpec.assertThatNext
import org.junit.Test

class GitHubLogicTest {
    private val updateSpec = UpdateSpec<GitHubModel, GitHubEvent, GitHubEffect>(GitHubLogic::update)
    private val emptyModel = GitHubModel.EMPTY

    @Test
    fun `when user types in a username, then search button is enabled`() {
        val username = "nitesh"

        updateSpec
            .given(emptyModel)
            .`when`(UsernameChangedEvent(username))
            .then(
                assertThatNext(
                    hasModel(emptyModel.usernameChanged(username)),
                    hasNoEffects()
                )
            )
    }

    @Test
    fun `when user clears the username, then search button is disabled`() {
        val hasUsernameModel = emptyModel.usernameChanged("nitesh")

        updateSpec
            .given(hasUsernameModel)
            .`when`(UsernameClearedEvent)
            .then(
                assertThatNext(
                    hasModel(emptyModel),
                    hasNoEffects()
                )
            )
    }

    @Test
    fun `when user clicks search, then make a network call`() {
        val username = "jakewharton"
        val hasUsernameModel = emptyModel.usernameChanged(username)
        val fetchingFollowersModel = hasUsernameModel.fetchingFollowers()

        updateSpec
            .given(hasUsernameModel)
            .`when`(FetchFollowersEvent(username))
            .then(
                assertThatNext(
                    hasModel(fetchingFollowersModel),
                    hasEffects(FetchFollowersEffect(username) as GitHubEffect)
                )
            )
    }
}
