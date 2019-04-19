package io.redgreen.benchpress.login


import com.spotify.mobius.Next
import com.spotify.mobius.Next.next
import com.spotify.mobius.Update

object LoginLogic : Update<LoginModel, LoginEvent, LoginEffect>{
    override fun update(model: LoginModel, event: LoginEvent): Next<LoginModel, LoginEffect> {
        return when (event) {

            is InputEmailEvent -> {
                next(
                    model.inputEmail(event.email)
                )
            }

            is InputPasswordEvent -> {
                next(
                    model.inputPassword(event.password)
                )
            }

            is AttemptLoginEvent -> {
                next(
                    model.attemptLogin(),
                    setOf(ApiCallEffect(LoginRequest(model.email,model.password)))
                )
            }

            is LoginSuccessEvent -> {
                next(
                    model.loginSuccess(),
                    setOf(SaveTokenEffect(event.response.token))
                )
            }

            is LoginAuthFailedEvent -> {
                next(
                    model.authError(),
                    setOf(ClearFieldsEffect)
                )
            }

            is LoginTimeoutFailedEvent -> {
                next(
                    model.apiError(),
                    setOf(RetryEffect)
                )
            }

            is TokenSavedEvent -> {
                next(
                    model,
                    setOf(NavigateEffect(NavigateTo.HOME))
                )
            }

            is RetryEvent -> {
                next(
                    model,
                    setOf(SaveTokenEffect(event.token))
                )
            }
            else -> TODO()
        }
    }
}
