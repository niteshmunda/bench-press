package io.redgreen.benchpress.github

import com.spotify.mobius.test.NextMatchers.*
import com.spotify.mobius.test.UpdateSpec
import com.spotify.mobius.test.UpdateSpec.assertThatNext
import io.redgreen.benchpress.github.domain.User
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

        updateSpec
            .given(hasUsernameModel)
            .`when`(FetchFollowersEvent)
            .then(
                assertThatNext(
                    hasModel(hasUsernameModel.fetchingFollowers()),
                    hasEffects(FetchFollowersEffect(username) as GitHubEffect)
                )
            )
    }

    @Test
    fun `when user has followers, then show a list of followers`() {
        val fetchingFollowersModel = emptyModel
            .usernameChanged("jakewharton")
            .fetchingFollowers()
        val followers = listOf(User("tom", "https://someurl.jpg/"))

        updateSpec
            .given(fetchingFollowersModel)
            .`when`(FollowersFetchedEvent(followers))
            .then(
                assertThatNext(
                    hasModel(fetchingFollowersModel.followersFetchedSuccess(followers)),
                    hasNoEffects()
                )
            )
    }

    @Test
    fun `when user does not have followers, then show no followers message`() {
        val fetchingFollowersModel = emptyModel
            .usernameChanged("jakewharton")
            .fetchingFollowers()

        updateSpec
            .given(fetchingFollowersModel)
            .`when`(NoFollowersEvent)
            .then(
                assertThatNext(
                    hasModel(fetchingFollowersModel.noFollowers()),
                    hasNoEffects()
                )
            )
    }

    @Test
    fun `when fetching followers failed, then show fetch failed error`() {
        val fetchingFollowersModel = emptyModel
            .usernameChanged("jakewharton")
            .fetchingFollowers()

        updateSpec
            .given(fetchingFollowersModel)
            .`when`(FollowersFetchFailedEvent)
            .then(
                assertThatNext(
                    hasModel(fetchingFollowersModel.followersFetchFailed()),
                    hasNoEffects()
                )
            )
    }

    @Test
    fun `when user taps retry, then retry fetching followers`() {
        val username = "nitesh"
        val followersFetchFailed = emptyModel
            .usernameChanged(username)
            .fetchingFollowers()
            .followersFetchFailed()

        updateSpec
            .given(followersFetchFailed)
            .`when`(RetryFetchFollowersEvent)
            .then(
                assertThatNext(
                    hasModel(followersFetchFailed.fetchingFollowers()),
                    hasEffects(FetchFollowersEffect(username) as GitHubEffect)
                )
            )
    }

    @Test
    fun `when username does not exist, then show no username error`() {
        val fetchingFollowersModel = emptyModel
            .usernameChanged("not-found-username")
            .fetchingFollowers()

        updateSpec
            .given(fetchingFollowersModel)
            .`when`(UsernameNotFoundEvent)
            .then(
                assertThatNext(
                    hasModel(fetchingFollowersModel.usernameNotFound()),
                    hasNoEffects()
                )
            )
    }

    @Test
    fun `when user clears the username (has followers), then go to empty state`() {
        val followers = listOf(User("tom", "https://someurl.jpg/"))
        val followersFetchedModel = emptyModel
            .usernameChanged("nitesh")
            .followersFetchedSuccess(followers)

        updateSpec
            .given(followersFetchedModel)
            .`when`(UsernameClearedEvent)
            .then(
                assertThatNext(
                    hasModel(emptyModel),
                    hasNoEffects()
                )
            )
    }

    @Test
    fun `when user clears the username (no followers), then go to empty state`() {
        val followersFetchedModel = emptyModel
            .usernameChanged("nitesh")
            .noFollowers()

        updateSpec
            .given(followersFetchedModel)
            .`when`(UsernameClearedEvent)
            .then(
                assertThatNext(
                    hasModel(emptyModel),
                    hasNoEffects()
                )
            )
    }

    @Test
    fun `when user clears the username (error), then go to empty state`() {
        val followersFetchedFail = emptyModel
            .usernameChanged("nitesh")
            .followersFetchFailed()

        updateSpec
            .given(followersFetchedFail)
            .`when`(UsernameClearedEvent)
            .then(
                assertThatNext(
                    hasModel(emptyModel),
                    hasNoEffects()
                )
            )
    }

    @Test
    fun `when user clears the username (non-existent username), then go to empty state`() {
        val usernameNotFoundModel = emptyModel
            .usernameChanged("nitesh")
            .fetchingFollowers()
            .usernameNotFound()

        updateSpec
            .given(usernameNotFoundModel)
            .`when`(UsernameClearedEvent)
            .then(
                assertThatNext(
                    hasModel(emptyModel),
                    hasNoEffects()
                )
            )
    }

    @Test
    fun `when user changes the username, then model should reset (have default values)`() {
        val followers = listOf(User("tom", "https://someurl.jpg/"))
        val usernameModel = emptyModel
            .usernameChanged("nitesh")
            .followersFetchedSuccess(followers)

        val usernameChangedModel = emptyModel
            .usernameChanged("nite")

        updateSpec
            .given(usernameModel)
            .`when`(UsernameChangedEvent("nite"))
            .then(
                assertThatNext(
                    hasModel(usernameChangedModel),
                    hasNoEffects()
                )
            )
    }

}
