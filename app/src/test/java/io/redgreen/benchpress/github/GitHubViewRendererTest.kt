package io.redgreen.benchpress.github

import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.verifyNoMoreInteractions
import org.junit.Test

class GitHubViewRendererTest {
    @Test
    fun `it can render empty state`() {
        // given
        val view = mock<GitHubView>()
        val viewRenderer = GitHubViewRenderer(view)
        val emptyModel = GitHubModel.EMPTY

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
}
