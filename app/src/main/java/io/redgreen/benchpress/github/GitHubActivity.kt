package io.redgreen.benchpress.github

import android.content.Context
import android.content.Intent
import android.text.Editable
import android.util.Log
import android.view.View
import com.spotify.mobius.Next
import io.reactivex.ObservableTransformer
import io.reactivex.Single
import io.redgreen.benchpress.R
import io.redgreen.benchpress.architecture.android.BaseActivity
import io.redgreen.benchpress.architecture.android.listener.TextWatcherAdapter
import io.redgreen.benchpress.github.domain.User
import io.redgreen.benchpress.github.http.GitHubApi
import kotlinx.android.synthetic.main.github_followers_layout.*
import timber.log.Timber
import kotlin.LazyThreadSafetyMode.NONE

class GitHubActivity : BaseActivity<GitHubModel, GitHubEvent, GitHubEffect>(), GitHubView {
    companion object {
        fun start(context: Context) {
            context.startActivity(Intent(context, GitHubActivity::class.java))
        }
    }

    private val viewRenderer by lazy(NONE) {
        GitHubViewRenderer(this)
    }

    private val gitHubApi by lazy(NONE) {
        object : GitHubApi {
            override fun getFollowers(username: String): Single<List<User>> {
                return Single.just(
                    listOf(
                        User("nitesh", "some-url"),
                        User("ragunath", "some-other-url")
                    )
                )
            }
        }
    }

    override fun layoutResId(): Int {
        return R.layout.github_followers_layout
    }

    override fun setup() {
        username_text.addTextChangedListener(object : TextWatcherAdapter() {
            override fun afterTextChanged(s: Editable) {
                val username = s.toString()
                val usernameEvent = if (username.isNotBlank()) UsernameChangedEvent(username) else UsernameClearedEvent
                eventSource.notifyEvent(usernameEvent)
            }
        })
        search_button.setOnClickListener { eventSource.notifyEvent(FetchFollowersEvent(username_text.text.toString())) }
    }

    override fun initialModel(): GitHubModel {
        return GitHubModel.EMPTY
    }

    override fun updateFunction(
        model: GitHubModel,
        event: GitHubEvent
    ): Next<GitHubModel, GitHubEffect> {
        val next = GitHubLogic.update(model, event)
        Timber.log(Log.DEBUG, "$event -> $model = ${next.modelOrElse(GitHubModel.EMPTY)}")
        return next
    }

    override fun render(model: GitHubModel) {
        viewRenderer.render(model)
    }

    override fun effectHandler(): ObservableTransformer<GitHubEffect, GitHubEvent> {
        return GitHubEffectHandler.createHandler(gitHubApi)
    }

    override fun disableSearchButton() {
        search_button.isEnabled = false
    }

    override fun hideFollowers() {
        list_item.visibility = View.INVISIBLE
    }

    override fun enableUsernameTextView() {
        username_text.isEnabled = true
        username_text.isClickable = true
    }

    override fun hideNoFollowersMessage() {
        no_followers_text.visibility = View.GONE
    }

    override fun hideRetryMessage() {
        something_went_wrong_text.visibility = View.GONE
    }

    override fun hideUsernameNotFoundMessage() {
        user_not_exist_text.visibility = View.GONE
    }

    override fun enableSearchButton() {
        search_button.isEnabled = true
    }

    override fun disableUsernameTextView() {
        username_text.isEnabled = false
        username_text.isClickable = false
    }

    override fun showProgress() {
        progress.visibility = View.VISIBLE
    }

    override fun showFollowers(followers: List<User>) {
        list_item.visibility = View.VISIBLE
        // TODO Implement the adapter!
    }

    override fun hideProgress() {
        progress.visibility = View.GONE
    }

    override fun showNoFollowersMessage() {
        no_followers_text.visibility = View.VISIBLE
    }

    override fun showRetryMessage() {
        something_went_wrong_text.visibility = View.VISIBLE
    }

    override fun showUsernameNotFoundMessage() {
        user_not_exist_text.visibility = View.VISIBLE
    }
}
