package io.redgreen.benchpress.github

import com.spotify.mobius.test.NextMatchers.hasModel
import com.spotify.mobius.test.NextMatchers.hasNoEffects
import com.spotify.mobius.test.UpdateSpec
import com.spotify.mobius.test.UpdateSpec.assertThatNext
import org.junit.Test

class GitHubLogicTest {
    @Test
    fun `when user types in a username, then search button is enabled`() {
        val updateSpec = UpdateSpec<GitHubModel, GitHubEvent, GitHubEffect>(GitHubLogic::update)
        val emptyModel = GitHubModel.EMPTY_STATE
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
}
