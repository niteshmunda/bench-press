package io.redgreen.benchpress.github

import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.whenever
import io.reactivex.Single
import io.redgreen.benchpress.github.domain.User
import io.redgreen.benchpress.github.http.GitHubApi
import io.redgreen.benchpress.test.EffectHandlerTestCase
import org.junit.Test

class GitHubEffectHandlerTest {
    private val gitHubApi = mock<GitHubApi>()
    private val username = "jakewharton"
    private val effectHandler = GitHubEffectHandler.createHandler(gitHubApi)
    private val testCase = EffectHandlerTestCase(effectHandler)

    @Test
    fun `when fetch followers has followers, then dispatch followers fetched event`() {
        // given
        val fetchFollowersEffect = FetchFollowersEffect(username)
        val followers = listOf(
            User("nitesh", "https://imagekit.io/nitesh.png")
        )
        whenever(gitHubApi.getFollowers(username))
            .thenReturn(Single.just(followers))

        // when
        testCase.dispatchEffect(fetchFollowersEffect)

        // then
        testCase.assertOutgoingEvents(FollowersFetchedEvent(followers))
    }

    @Test
    fun `when fetch followers has no followers, then dispatch no followers event`() {
        // given
        val fetchFollowersEffect = FetchFollowersEffect(username)
        val noFollowers = emptyList<User>()
        whenever(gitHubApi.getFollowers(username))
            .thenReturn(Single.just(noFollowers))

        // when
        testCase.dispatchEffect(fetchFollowersEffect)

        // then
        testCase.assertOutgoingEvents(NoFollowersEvent)
    }
}
