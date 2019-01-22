package io.redgreen.benchpress.login

import com.google.common.truth.Truth.assertThat
import com.spotify.mobius.test.NextMatchers.*
import com.spotify.mobius.test.UpdateSpec
import com.spotify.mobius.test.UpdateSpec.assertThatNext
import org.junit.Test

class LoginLogicTest {

    private val updateSpec = UpdateSpec<LoginModel, LoginEvent, LoginEffect>(LoginLogic::update)

    private val validEmail = "valid.email@test.com"
    private val validPassword = "validPassword123"

    @Test
    fun `user can input email`() {
        val emptyModel = LoginModel.EMPTY
        val anEmail = "me@dunzo.in"

        updateSpec.given(emptyModel)
            .`when`(InputEmailEvent(anEmail))
            .then(
                assertThatNext(
                    hasModel(emptyModel.inputEmail(anEmail)),
                    hasNoEffects()
                )
            )
    }

    @Test
    fun `user can input password`() {
        val emptyModel = LoginModel.EMPTY
        val password = "secret"

        updateSpec.given(emptyModel)
            .`when`(InputPasswordEvent(password))
            .then(
                assertThatNext(
                    hasModel(emptyModel.inputPassword(password)),
                    hasNoEffects()
                )
            )
    }

    @Test
    fun `when user enters invalid email and password, then user cannot press login button`() {
        val emptyModel = LoginModel.EMPTY
        val anInvalidEmail = "masdad"
        val anInvalidPassword = "123"

        val invalidModel = emptyModel
            .inputEmail(anInvalidEmail)
            .inputPassword(anInvalidPassword)

        updateSpec.given(emptyModel)
            .`when`(InputEmailEvent(anInvalidEmail), InputPasswordEvent(anInvalidPassword))
            .then(
                assertThatNext(
                    hasModel(invalidModel),
                    hasNoEffects()
                )
            )

        assertThat(invalidModel.isReadyForLogin)
            .isFalse()
    }

    @Test
    fun `when user enters valid email and password, then user can press login button`() {
        val emptyModel = LoginModel.EMPTY

        val validModel = emptyModel
            .inputEmail(validEmail)
            .inputPassword(validPassword)

        updateSpec.given(emptyModel)
            .`when`(InputEmailEvent(validEmail), InputPasswordEvent(validPassword))
            .then(
                assertThatNext(
                    hasModel(validModel),
                    hasNoEffects()
                )
            )
        assertThat(validModel.isReadyForLogin)
            .isTrue()
    }

    // This can have combinations of email, password or both
    @Test
    fun `when user enters invalid email and password, then user cannot login starting with invalid model`() {
        val invalidModel = LoginModel("lalal", "1123")
        val anInvalidEmail = "masdad"
        val anInvalidPassword = "123"

        val yetAnotherInvalidModel = invalidModel
            .inputEmail(anInvalidEmail)
            .inputPassword(anInvalidPassword)

        updateSpec.given(invalidModel)
            .whenEvents(
                InputEmailEvent(anInvalidEmail),
                InputPasswordEvent(anInvalidPassword)
            ).then(
                assertThatNext(
                    hasModel(yetAnotherInvalidModel),
                    hasNoEffects()
                )
            )

        assertThat(yetAnotherInvalidModel.isReadyForLogin)
            .isFalse()
    }

    @Test
    fun `user can click on login button with valid credentials`() {
        val model = LoginModel(validEmail, validPassword) // valid model
        val loading = LoginModel(validEmail, validPassword, ApiState.LOADING) // valid model in loading state.

        updateSpec.given(model)
            .`when`(AttemptLoginEvent)
            .then(
                assertThatNext(
                    hasModel(loading),
                    hasEffects(ApiCallEffect(LoginRequest(validEmail, validPassword)) as LoginEffect)
                )
            )
    }

    @Test
    fun `when login api is successful token is saved`() {
        val model = LoginModel(validEmail, validPassword, ApiState.LOADING) // In loading state
        val successModel = LoginModel(validEmail, validPassword, ApiState.SUCCESS)

        updateSpec.given(model)
            .`when`(LoginSuccessEvent(LoginResponse("token")))
            .then(
                assertThatNext(
                    hasModel(successModel),
                    hasEffects(SaveTokenEffect as LoginEffect)
                )
            )
    }

    @Test
    fun `when login api fails with invalid credentials we clear fields`() {
        val model = LoginModel(validEmail, validPassword, ApiState.LOADING) // In loading state
        updateSpec.given(model)
            .`when`(LoginFailedEvent(NetworkError.AUTH_ERROR))
            .then(
                assertThatNext(
                    hasModel(LoginModel.EMPTY),
                    hasEffects(ClearFieldsEffect as LoginEffect)
                )
            )
    }

    @Test
    fun `when login api fails with network error we show retry`() {
        val model = LoginModel(validEmail, validPassword, ApiState.LOADING) // In loading state
        updateSpec.given(model)
            .`when`(LoginFailedEvent(NetworkError.TIMEOUT))
            .then(
                assertThatNext(
                    hasModel(model.apiError()),
                    hasEffects(RetryEffect as LoginEffect)
                )
            )
    }

    @Test
    fun `when token is saved user goes to home screen`() {
        val model = LoginModel(validEmail, validPassword, ApiState.SUCCESS)

        updateSpec.given(model)
            .`when`(SaveTokenEvent)
            .then(
                assertThatNext(
                    hasModel(model),
                    hasEffects(NaviagteEffect(NavigateTo.HOME) as LoginEffect)
                )
            )
    }

    @Test
    fun `user can retry save token`() {
        //
    }
}
