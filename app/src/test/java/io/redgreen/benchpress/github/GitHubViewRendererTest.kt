package io.redgreen.benchpress.github

import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.verifyNoMoreInteractions
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

        verifyNoMoreInteractions(view)
    }
}
