package io.redgreen.benchpress.login

import com.spotify.mobius.Next
import com.spotify.mobius.Next.next
import com.spotify.mobius.Update

object LoginLogic : Update<LoginModel, LoginEvent, LoginEffect> {
    override fun update(
        model: LoginModel,
        event: LoginEvent
    ): Next<LoginModel, LoginEffect> {
        return when (event) {

            is InputEmailEvent -> next(
                model.inputEmail(event.email)
            )
            is InputPasswordEvent -> next(
                model.inputPassword(event.password)
            )
            is AttemptLoginEvent -> next(
                model.apiCalled(),
                setOf(ApiCallEffect(LoginRequest(model.email, model.password)))
            )

            is SaveTokenEvent -> next(
                model,
                setOf(NavigateEffect(NavigateTo.HOME))
            )
            is LoginSuccessEvent -> next(
                model.apiSuccessful(),
                setOf(SaveTokenEffect)
            )
            is LoginFailedEvent -> {

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
