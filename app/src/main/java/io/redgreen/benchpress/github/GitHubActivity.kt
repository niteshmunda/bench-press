package io.redgreen.benchpress.github

import android.view.View
import com.spotify.mobius.Next
import io.reactivex.ObservableTransformer
import io.redgreen.benchpress.R
import io.redgreen.benchpress.architecture.android.BaseActivity
import kotlinx.android.synthetic.main.github_followers_layout.*
import kotlin.LazyThreadSafetyMode.NONE

class GitHubActivity : BaseActivity<GitHubModel, GitHubEvent, GitHubEffect>(), GitHubView {
    private val viewRenderer by lazy(NONE) {
        GitHubViewRenderer(this)
    }

    override fun layoutResId(): Int {
        return R.layout.github_followers_layout
    }

    override fun setup() {
        TODO("setup event listeners")
    }

    override fun initialModel(): GitHubModel {
        return GitHubModel.EMPTY
    }

    override fun updateFunction(
        model: GitHubModel,
        event: GitHubEvent
    ): Next<GitHubModel, GitHubEffect> {
        return GitHubLogic.update(model, event)
    }

    override fun render(model: GitHubModel) {
        viewRenderer.render(model)
    }

    override fun effectHandler(): ObservableTransformer<GitHubEffect, GitHubEvent> {
        TODO("not implemented")
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

    override fun showFollowers() {
        list_item.visibility = View.VISIBLE
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
