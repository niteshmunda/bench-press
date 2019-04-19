package io.redgreen.benchpress.github

import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.verifyNoMoreInteractions
import io.redgreen.benchpress.github.domain.User
import org.junit.Test

class GitHubViewRendererTest {
    private val view = mock<GitHubView>()
    private val viewRenderer = GitHubViewRenderer(view)
    private val emptyModel = GitHubModel.EMPTY

    @Test
    fun `it can render empty state`() {
        // when
        viewRenderer.render(emptyModel)

        // then
        verify(view).disableSearchButton()
        verify(view).hideFollowers()
        verify(view).enableUsernameTextView()
        verify(view).hideNoFollowersMessage()
        verify(view).hideRetryMessage()
        verify(view).hideUsernameNotFoundMessage()
        verify(view).showWelcomeMessage()

        verifyNoMoreInteractions(view)
    }

    @Test
    fun `it can render has username state`() {
        // given
        val hasUsernameState = emptyModel.usernameChanged("nitesh")

        // when
        viewRenderer.render(hasUsernameState)

        // then
        verify(view).enableSearchButton()
        verify(view).showWelcomeMessage()
        verify(view).hideProgress()
        verify(view).hideNoFollowersMessage()
        verify(view).hideRetryMessage()
        verify(view).hideUsernameNotFoundMessage()
        verify(view).hideFollowers()

        verifyNoMoreInteractions(view)
    }

    @Test
    fun `it can render loading state`() {
        // given
        val loadingState = emptyModel.usernameChanged("nitesh").fetchingFollowers()

        // when
        viewRenderer.render(loadingState)

        // then
        verify(view).disableSearchButton()
        verify(view).disableUsernameTextView()
        verify(view).showProgress()
        verify(view).hideRetryMessage()
        verify(view).hideWelcomeMessage()

        verifyNoMoreInteractions(view)
    }

    @Test
    fun `it can render followers state`() {
        // given
        val followers = listOf(User("tom", "https//someurl.jpg"))
        val hasFollowersState = emptyModel.usernameChanged("nitesh").followersFetchedSuccess(followers)

        // when
        viewRenderer.render(hasFollowersState)

        // then
        verify(view).enableSearchButton()
        verify(view).enableUsernameTextView()
        verify(view).showFollowers(followers)
        verify(view).hideProgress()

        verifyNoMoreInteractions(view)
    }

    @Test
    fun `it can render no followers state`() {
        // given
        val noFollowersState = emptyModel.usernameChanged("nitesh").noFollowers()

        // when
        viewRenderer.render(noFollowersState)

        // then
        verify(view).enableSearchButton()
        verify(view).enableUsernameTextView()
        verify(view).hideProgress()
        verify(view).showNoFollowersMessage()

        verifyNoMoreInteractions(view)
    }

    @Test
    fun `it can render Api error state`() {
        // given
        val apiErrorState = emptyModel.usernameChanged("nitesh").followersFetchFailed()

        // when
        viewRenderer.render(apiErrorState)

        // then
        verify(view).enableSearchButton()
        verify(view).enableUsernameTextView()
        verify(view).hideProgress()
        verify(view).showRetryMessage()

        verifyNoMoreInteractions(view)
    }

    @Test
    fun `it can render non-existent user state`() {
        // given
        val usernameNotFoundState = emptyModel.usernameChanged("nitesh").usernameNotFound()

        // when
        viewRenderer.render(usernameNotFoundState)

        // then
        verify(view).enableSearchButton()
        verify(view).enableUsernameTextView()
        verify(view).hideProgress()
        verify(view).showUsernameNotFoundMessage()

        verifyNoMoreInteractions(view)
    }
}
