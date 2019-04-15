package io.redgreen.benchpress.github.http

import io.reactivex.Single
import io.redgreen.benchpress.github.domain.User
import okhttp3.MediaType
import okhttp3.ResponseBody
import retrofit2.HttpException
import retrofit2.Response
import java.util.concurrent.TimeUnit

class StubGitHubApi : GitHubApi {
    override fun getFollowers(username: String): Single<List<User>> {
        val response = when (username) {
            "lonely" -> Single.just(emptyList())

            "nobody" -> Single.error(httpException())

            "nitesh" -> Single.just(
                listOf(
                    User("nitesh", "https://picsum.photos/200/300?image=0\n"),
                    User("ragunath", "https://picsum.photos/200/300?image=0\n")
                )
            )

            else -> Single.error(RuntimeException("Api Fetch Failed"))
        }

        return response.delay(1500, TimeUnit.MILLISECONDS)
    }

    private fun httpException(): HttpException {
        val errorResponse =  """
            {
                "message" : "Not Found",
                "documentation_url": "https://developer.github.com/v3/users/followers/#list-followers-of-a-user"
            }
        """.trimIndent()
        val response = Response.error<Any> (
            404,
            ResponseBody.create(
                MediaType.parse("application/json"),
                errorResponse
            )
        )
        return HttpException(response)
    }
}
