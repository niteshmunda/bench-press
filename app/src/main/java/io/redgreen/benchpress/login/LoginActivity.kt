package io.redgreen.benchpress.login

import android.content.Context
import android.content.Intent
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

class LoginActivity : BaseActivity<LoginModel, LoginEvent, LoginEffect>(), LoginActions {

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

        // Enable button only when both the credentials are correct.
        loginButton.isEnabled = model.isReadyForLogin
        loginButton.isClickable = model.isReadyForLogin

        emailTextInputLayout.error = if (model.showEmailError) {
            getString(R.string.invalid_email)
        } else {
            null
        }

        passwordTextInputLayout.error = if (model.showPasswordError) {
            getString(R.string.invalid_password)
        } else {
            null
        }

        authenticationProgressBar.visibility = if (model.apiState == ApiState.LOADING) {
            View.VISIBLE
        } else {
            View.GONE
        }
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
}
