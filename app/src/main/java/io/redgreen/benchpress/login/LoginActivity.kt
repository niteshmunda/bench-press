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
import io.redgreen.benchpress.login.scheduler.AppSchedulersImpl
import kotlinx.android.synthetic.main.login_activity.*

class  LoginActivity : BaseActivity<LoginModel, LoginEvent, LoginEffect>(), LoginAction, LoginView {

    private val renderer by lazy(LazyThreadSafetyMode.NONE){
        LoginViewRenderer(this)
    }
    override fun enableLogin() {
        loginButton.isEnabled = true
        loginButton.isClickable = true
    }

    override fun navigateToHome() {
        LaunchpadActivity.start(this)
    }

    override fun clearFields() {
        emailEditText.setText("")
        passwordEditText.setText("")
        loginButton.text = getString(R.string.login_text)
    }

    override fun retry() {
        subheaderTextView.setText(R.string.network_error)
        loginButton.text = getString(R.string.login_retry)    }

    override fun disableLogin() {
        loginButton.isEnabled = false
        loginButton.isClickable = false
    }

    override fun showEmailError(error: Int) {
        emailTextInputLayout.error = getString(error)
    }

    override fun showPasswordError(error: Int) {
        passwordTextInputLayout.error = getString(error)
    }

    override fun hideEmailError() {
        emailTextInputLayout.error = null
    }

    override fun hidePasswordError() {
        passwordTextInputLayout.error = null
    }

    override fun showProgressBar() {
        authenticationProgressBar.visibility = View.VISIBLE
    }

    override fun hideProgressBar() {
        authenticationProgressBar.visibility = View.GONE
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
        return LoginLogic.update(model,event)
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
}
