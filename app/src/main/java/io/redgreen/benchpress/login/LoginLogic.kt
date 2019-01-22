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

            is InputEmailEvent -> next(model.inputEmail(event.email))
            is InputPasswordEvent -> next(model.inputPassword(event.password))
            is AttemptLoginEvent -> next(model.apiCalled(), setOf(ApiCallEffect))

            else -> TODO()
        }
    }
}
