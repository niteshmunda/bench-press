package io.redgreen.benchpress.github

import android.content.Context
import android.content.Intent
import android.text.Editable
import android.util.Log
import android.view.View
import com.spotify.mobius.Next
import io.reactivex.ObservableTransformer
import io.redgreen.benchpress.R
import io.redgreen.benchpress.architecture.android.BaseActivity
import io.redgreen.benchpress.architecture.android.listener.TextWatcherAdapter
import io.redgreen.benchpress.architecture.threading.DefaultSchedulersProvider
import io.redgreen.benchpress.github.domain.User
import io.redgreen.benchpress.github.http.GitHubApi
import kotlinx.android.synthetic.main.github_followers_layout.*
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import okhttp3.logging.HttpLoggingInterceptor.Level.HEADERS
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.moshi.MoshiConverterFactory
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

    private val schedulersProvider = DefaultSchedulersProvider()

    private val gitHubApi by lazy(NONE) {
        val okHttpClient = OkHttpClient
            .Builder()
            .addInterceptor(HttpLoggingInterceptor().setLevel(HEADERS))
            .build()

        return@lazy Retrofit
            .Builder()
            .baseUrl("https://api.github.com/")
            .client(okHttpClient)
            .addCallAdapterFactory(RxJava2CallAdapterFactory.createWithScheduler(schedulersProvider.io))
            .addConverterFactory(MoshiConverterFactory.create())
            .build()
            .create(GitHubApi::class.java)
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
        search_button.setOnClickListener { eventSource.notifyEvent(FetchFollowersEvent) }
        retry_button.setOnClickListener { eventSource.notifyEvent(RetryFetchFollowersEvent) }
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
        return GitHubEffectHandler.createHandler(gitHubApi, schedulersProvider)
    }

    override fun showWelcomeMessage() {
        search_something_text.visibility = View.VISIBLE
    }

    override fun hideWelcomeMessage() {
        search_something_text.visibility = View.INVISIBLE
    }

    override fun disableSearchButton() {
        search_button.isEnabled = false
    }

    override fun hideFollowers() {
        followersListView.visibility = View.INVISIBLE
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
        followersListView.visibility = View.VISIBLE
        followersListView.adapter = CustomListAdapter(this, followers)
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
