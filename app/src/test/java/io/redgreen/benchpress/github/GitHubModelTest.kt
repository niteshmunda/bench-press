package io.redgreen.benchpress.github

import com.google.common.truth.Truth.assertThat
import org.junit.Test

class GitHubModelTest {
    // TODO 1. Same as previous. How do we know we are disabling the search button. 'canSearch'
    // TODO 2. Username with leading/trailing spaces.

    @Test
    fun `when the username is empty, then user cannot search`() {
        val emptyUsernameModel = GitHubModel
            .EMPTY.usernameChanged("")

        assertThat(emptyUsernameModel.canSearch)
            .isFalse()
    }


}