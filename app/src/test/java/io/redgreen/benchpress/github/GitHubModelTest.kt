package io.redgreen.benchpress.github

import com.google.common.truth.Truth.assertThat
import org.junit.Ignore
import org.junit.Test

class GitHubModelTest {
    // TODO 1. Same as previous. How do we know we are disabling the search button. 'canSearch'
    // TODO 2. Username with leading/trailing spaces.

    @Test
    fun `when the username is empty, then user cannot search`() {
        val emptyUsernameModel = GitHubModel.EMPTY
            .usernameChanged("")

        assertThat(emptyUsernameModel.canSearch)
            .isFalse()
    }

    @Test
    fun `when username is only white spaces, then user cannot search`() {
        val blankUsernameModel = GitHubModel.EMPTY
            .usernameChanged("  ")

        assertThat(blankUsernameModel.canSearch)
            .isFalse()
    }

    @Test
    fun `when username has leading white spaces, then user cannot search`() {
        val leadingWhitespaceUsernameModel = GitHubModel.EMPTY
            .usernameChanged("   nitesh")

        assertThat(leadingWhitespaceUsernameModel.canSearch)
            .isTrue()
    }

    @Ignore
    @Test
    fun `when username has trailing white spaces, then user cannot search`() {
        val trailingWhitespaceUsernameModel = GitHubModel.EMPTY
            .usernameChanged("nitesh  ")

        assertThat(trailingWhitespaceUsernameModel.canSearch)
            .isTrue()
    }
}