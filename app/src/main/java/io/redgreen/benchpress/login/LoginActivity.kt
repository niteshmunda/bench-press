package io.redgreen.benchpress.login

import android.content.Context
import android.content.Intent
import android.support.annotation.StringRes
import android.text.Editable
import android.view.View
import com.spotify.mobius.Next
import io.reactivex.ObservableTransformer
import io.redgreen.benchpress.R
import io.redgreen.benchpress.architecture.android.BaseActivity
import io.redgreen.benchpress.architecture.android.listener.TextWatcherAdapter
import io.redgreen.benchpress.launchpad.LaunchpadActivity
import io.redgreen.benchpress.login.api.ApiServiceImpl
import io.redgreen.benchpress.login.db.RepositoryImpl
import io.redgreen.benchpress.login.schedulers.AppSchedulersImpl
import kotlinx.android.synthetic.main.login_activity.*
import timber.log.Timber

class LoginActivity : BaseActivity<LoginModel, LoginEvent, LoginEffect>(), LoginActions, LoginView {

    private val renderer by lazy(LazyThreadSafetyMode.NONE) {
        LoginViewRenderer(this)
    }

    override fun layoutResId(): Int {
        return R.layout.login_activity
    }

    override fun setup() {
        emailEditText.addTextChangedListener(object : TextWatcherAdapter() {
            override fun afterTextChanged(s: Editable) {
                eventSource.notifyEvent(InputEmailEvent(s.toString()))
            }
        })
        passwordEditText.addTextChangedListener(object : TextWatcherAdapter() {
            override fun afterTextChanged(s: Editable) {
                eventSource.notifyEvent(InputPasswordEvent(s.toString()))
            }
        })
        loginButton.setOnClickListener {
            eventSource.notifyEvent(AttemptLoginEvent)
        }
    }

    override fun initialModel(): LoginModel {
        return LoginModel.EMPTY
    }

    override fun updateFunction(model: LoginModel, event: LoginEvent): Next<LoginModel, LoginEffect> {
        return LoginLogic.update(model, event)
    }

    override fun render(model: LoginModel) {
        renderer.render(model)
    }

    override fun effectHandler(): ObservableTransformer<LoginEffect, LoginEvent> {
        return LoginEffectHandler.create(this, ApiServiceImpl(), RepositoryImpl(), AppSchedulersImpl())
    }

    companion object {
        fun start(context: Context) {
            context.startActivity(Intent(context, LoginActivity::class.java))
        }
    }

    override fun navigateToHome() {
        Timber.i("Navigating to home screen")
        LaunchpadActivity.start(this)
    }

    override fun clearFields() {
        Timber.i("Clearing fields")
        emailEditText.setText("")
        passwordEditText.setText("")
        loginButton.text = getString(R.string.login_text)
    }

    override fun retry() {
        Timber.i("Network call failed due to api call")
        subheaderTextView.setText(R.string.network_error)
        loginButton.text = getString(R.string.login_retry)
    }

    override fun enableLogin() {
        loginButton.isEnabled = true
        loginButton.isClickable = true
    }

    override fun disableLogin() {
        loginButton.isEnabled = false
        loginButton.isClickable = false
    }

    override fun showEmailError(@StringRes error: Int) {
        emailTextInputLayout.error = getString(error)
    }

    override fun showPasswordError(@StringRes error: Int) {
        passwordTextInputLayout.error = getString(error)
    }

    override fun hideEmailError() {
        emailTextInputLayout.error = null
    }

    override fun hidePasswordError() {
        passwordTextInputLayout.error = null
    }

    override fun showProgressbar() {
        authenticationProgressBar.visibility = View.VISIBLE
    }

    override fun hideProgressbar() {
        authenticationProgressBar.visibility = View.GONE
    }
}
