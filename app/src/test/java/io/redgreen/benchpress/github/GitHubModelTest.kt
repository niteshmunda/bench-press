package io.redgreen.benchpress.github

import com.google.common.truth.Truth.assertThat
import org.junit.Test

class GitHubModelTest {
    @Test
    fun `when the username is empty, then user cannot search`() {
        val emptyUsernameModel = GitHubModel.EMPTY
            .usernameChanged("")

        assertThat(emptyUsernameModel.canSearch)
            .isFalse()
    }

    @Test
    fun `when username is only whitespaces, then user cannot search`() {
        val blankUsernameModel = GitHubModel.EMPTY
            .usernameChanged("  ")

        assertThat(blankUsernameModel.canSearch)
            .isFalse()
    }

    @Test
    fun `when username has leading whitespaces, then user cannot search`() {
        val leadingWhitespaceUsernameModel = GitHubModel.EMPTY
            .usernameChanged("   nitesh")

        assertThat(leadingWhitespaceUsernameModel.canSearch)
            .isTrue()
    }

    @Test
    fun `when username has trailing whitespaces, then user cannot search`() {
        val trailingWhitespaceUsernameModel = GitHubModel.EMPTY
            .usernameChanged("nitesh  ")

        assertThat(trailingWhitespaceUsernameModel.canSearch)
            .isTrue()
    }

    @Test
    fun `when username has leading whitespaces, then it will trim the whitespaces`() {
        val leadingWhitespacesUsernameModel = GitHubModel.EMPTY
            .usernameChanged("  nitesh")

        assertThat(leadingWhitespacesUsernameModel.username)
            .isEqualTo("nitesh")
    }

    @Test
    fun `when username has trailing whitespaces, then it will trim the whitespaces`() {
        val trailingUsernameModel = GitHubModel.EMPTY
            .usernameChanged("nitesh  ")

        assertThat(trailingUsernameModel.username)
            .isEqualTo("nitesh")
    }
}
