package io.redgreen.benchpress.github

data class GitHubModel(
    val username: String
) {
    companion object {
        val EMPTY = GitHubModel("")
    }

    fun usernameChanged(username: String): GitHubModel =
        copy(username = username)
}
