package io.redgreen.benchpress.github

import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.whenever
import io.reactivex.Single
import io.redgreen.benchpress.github.domain.User
import io.redgreen.benchpress.github.http.GitHubApi
import io.redgreen.benchpress.test.EffectHandlerTestCase
import org.junit.Test

class GitHubEffectHandlerTest {
    @Test
    fun `when fetch followers has followers, then dispatch followers fetched event`() {
        // given - API
        val gitHubApi = mock<GitHubApi>()
        val followers = listOf(
            User("nitesh", "https://imagekit.io/nitesh.png")
        )
        val username = "jakewharton"
        whenever(gitHubApi.getFollowers(username))
            .thenReturn(Single.just(followers))

        // given - Effect Handler
        val fetchFollowersEffect = FetchFollowersEffect(username)
        val effectHandler = GitHubEffectHandler.createHandler(gitHubApi)
        val testCase = EffectHandlerTestCase(effectHandler)

        // when
        testCase.dispatchEffect(fetchFollowersEffect)

        // then
        testCase.assertOutgoingEvents(FollowersFetchedEvent(followers))
    }

}