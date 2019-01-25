package io.redgreen.benchpress.login

import com.spotify.mobius.Next
import com.spotify.mobius.Next.next
import com.spotify.mobius.Update
import timber.log.Timber

object LoginLogic : Update<LoginModel, LoginEvent, LoginEffect> {
    override fun update(
        model: LoginModel,
        event: LoginEvent
    ): Next<LoginModel, LoginEffect> {
        return when (event) {

            is InputEmailEvent -> {
                Timber.i("InputEmailEvent")
                next(
                    model.inputEmail(event.email)
                )
            }
            is InputPasswordEvent -> {
                Timber.i("InputPasswordEvent")
                next(
                    model.inputPassword(event.password)
                )
            }
            is AttemptLoginEvent -> {
                Timber.i("AttemptLoginEvent")
                next(
                    model.apiCalled(),
                    setOf(ApiCallEffect(LoginRequest(model.email, model.password)))
                )
            }

            is SaveTokenEvent -> {
                Timber.i("SaveTokenEvent")
                next(
                    model,
                    setOf(NavigateEffect(NavigateTo.HOME))
                )
            }
            is LoginSuccessEvent -> {
                Timber.i("LoginSuccessEvent")
                next(
                    model.apiSuccessful(),
                    setOf(SaveTokenEffect(event.response.token))
                )
            }
            is LoginFailedEvent -> {

                Timber.i("LoginFailedEvent")
                when (event.error) {
                    NetworkError.AUTH_ERROR -> next<LoginModel, LoginEffect>(
                        model.authError(),
                        setOf(ClearFieldsEffect)
                    )
                    NetworkError.TIMEOUT -> next<LoginModel, LoginEffect>(
                        model.apiError(),
                        setOf(RetryEffect)
                    )
                    else -> next<LoginModel, LoginEffect>(model.authError())
                }
            }
            else -> TODO()
        }
    }
}
