package io.redgreen.benchpress.login

import android.content.Context
import android.content.Intent
import com.spotify.mobius.Next
import io.reactivex.ObservableTransformer
import io.redgreen.benchpress.R
import io.redgreen.benchpress.architecture.android.BaseActivity
import io.redgreen.benchpress.launchpad.LaunchpadActivity
import io.redgreen.benchpress.login.api.ApiServiceImpl
import io.redgreen.benchpress.login.db.RepositoryImpl
import kotlinx.android.synthetic.main.login_activity.*
import timber.log.Timber

class LoginActivity : BaseActivity<LoginModel, LoginEvent, LoginEffect>(), LoginActions {

    override fun layoutResId(): Int {
        return R.layout.login_activity
    }

    override fun setup() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun initialModel(): LoginModel {
        return LoginModel.EMPTY
    }

    override fun updateFunction(model: LoginModel, event: LoginEvent): Next<LoginModel, LoginEffect> {
        return LoginLogic.update(model, event)
    }

    override fun render(model: LoginModel) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun effectHandler(): ObservableTransformer<LoginEffect, LoginEvent> {
        return LoginEffectHandler.create(this, ApiServiceImpl(), RepositoryImpl())
    }

    companion object {
        fun start(context: Context) {
            context.startActivity(Intent(context, LoginActivity::class.java))
        }
    }

    override fun navigateToHome() {
        // no-op
        Timber.i("Navigating to home screen")
        LaunchpadActivity.start(this)
    }

    override fun clearFields() {
        Timber.i("Clearing fields")
        emailEditText.setText("")
        passwordEditText.setText("")
    }

    override fun retry() {
        Timber.i("Network call failed due to api call")
        subheaderTextView.setText(R.string.network_error)
    }
}
