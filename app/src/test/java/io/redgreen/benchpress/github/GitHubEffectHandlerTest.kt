package io.redgreen.benchpress.github

import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.whenever
import io.reactivex.Single
import io.redgreen.benchpress.github.domain.User
import io.redgreen.benchpress.github.http.GitHubApi
import io.redgreen.benchpress.test.EffectHandlerTestCase
import io.redgreen.benchpress.test.ImmediateSchedulersProvider
import okhttp3.MediaType
import okhttp3.ResponseBody
import org.junit.Test
import retrofit2.HttpException
import retrofit2.Response

class GitHubEffectHandlerTest {
    private val gitHubApi = mock<GitHubApi>()
    private val username = "jakewharton"
    private val effectHandler = GitHubEffectHandler.createHandler(gitHubApi, ImmediateSchedulersProvider())
    private val testCase = EffectHandlerTestCase(effectHandler)
    private val fetchFollowersEffect = FetchFollowersEffect(username)

    @Test
    fun `when fetch followers has followers, then dispatch followers fetched event`() {
        // given
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
        val noFollowers = emptyList<User>()
        whenever(gitHubApi.getFollowers(username))
            .thenReturn(Single.just(noFollowers))

        // when
        testCase.dispatchEffect(fetchFollowersEffect)

        // then
        testCase.assertOutgoingEvents(NoFollowersEvent)
    }

    @Test
    fun `when fetching followers failed, then dispatch fetch followers failed event`() {
        // given
        whenever(gitHubApi.getFollowers(username))
            .thenReturn(Single.error(RuntimeException("Something went wrong!")))

        // when
        testCase.dispatchEffect(fetchFollowersEffect)

        // then
        testCase.assertOutgoingEvents(FollowersFetchFailedEvent)
    }

    @Test
    fun `when user is not found, then dispatch username not found event`() {
        // given
        val nonExistentUsername = "phantom-user"
        val usernameNotFoundException = getUsernameNotFoundException()
        whenever(gitHubApi.getFollowers(nonExistentUsername))
            .thenReturn(Single.error(usernameNotFoundException))

        // when
        testCase.dispatchEffect(FetchFollowersEffect(nonExistentUsername))

        // then
        testCase.assertOutgoingEvents(UsernameNotFoundEvent)
    }

    private fun getUsernameNotFoundException(): HttpException {
        val usernameNotFoundContent = """
            {
                "message": "Not Found",
                "documentation_url": "https://developer.github.com/v3/users/followers/#list-followers-of-a-user"
            }
        """.trimIndent()
        val usernameNotFoundError = Response.error<Any>(
            404,
            ResponseBody.create(
                MediaType.parse("application/json"),
                usernameNotFoundContent
            )
        )
        return HttpException(usernameNotFoundError)
    }
}
