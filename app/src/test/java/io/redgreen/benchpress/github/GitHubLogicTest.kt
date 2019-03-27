package io.redgreen.benchpress.github

import com.spotify.mobius.test.NextMatchers.hasModel
import com.spotify.mobius.test.NextMatchers.hasNoEffects
import com.spotify.mobius.test.UpdateSpec
import com.spotify.mobius.test.UpdateSpec.assertThatNext
import org.junit.Test

class GitHubLogicTest {
    private val updateSpec = UpdateSpec<GitHubModel, GitHubEvent, GitHubEffect>(GitHubLogic::update)

    @Test
    fun `when user types in a username, then search button is enabled`() {
        val emptyModel = GitHubModel.EMPTY
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
        val emptyModel = GitHubModel.EMPTY
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
}
