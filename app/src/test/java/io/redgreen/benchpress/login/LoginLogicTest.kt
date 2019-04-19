package io.redgreen.benchpress.login

import com.google.common.truth.Truth.assertThat
import com.spotify.mobius.test.NextMatchers.*
import com.spotify.mobius.test.UpdateSpec
import com.spotify.mobius.test.UpdateSpec.assertThatNext
import org.junit.Test

class LoginLogicTest{
    private val updateSpec = UpdateSpec<LoginModel, LoginEvent, LoginEffect> (LoginLogic::update)

    private val validEmail = "validEmail@test.com"
    private val validPassword = "validPassword"

    private val invalidEmail = "something"
    private val invalidPassword = "wrong"

    @Test
    fun `user can input email`() {
        val emptyModel = LoginModel.EMPTY
        val anEmail = "some@test.com"

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
    fun `user can input password` (){
        val emptyModel = LoginModel.EMPTY
        val anPassword = "somePassword"

        updateSpec.given(emptyModel)
            .`when`(InputPasswordEvent(anPassword))
            .then(
                assertThatNext(
                    hasModel(emptyModel.inputPassword(anPassword)),
                    hasNoEffects()
                )
            )
    }

    @Test
    fun `user enters invalid email and password and is unable to login` () {
        val emptyModel = LoginModel.EMPTY
        val invalidModel = emptyModel
            .inputEmail(invalidEmail)
            .inputPassword(invalidPassword)


        updateSpec.given(emptyModel)
            .`when`(InputEmailEvent(invalidEmail), InputPasswordEvent(invalidPassword))
            .then(
                assertThatNext(
                    hasModel(invalidModel),
                    hasNoEffects()
                )
            )

        assertThat(invalidModel.canLogin)
            .isFalse()
    }

    @Test
    fun `user enter valid email and password and is able to login` () {
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

        assertThat(validModel.canLogin)
            .isTrue()
    }

    @Test
    fun `user clicks the login button with valid email and valid password and attempted login` () {
        val validModel = LoginModel.EMPTY
            .inputEmail(validEmail)
            .inputPassword(validPassword)

        val loadingModel = LoginModel(validEmail,validPassword,ApiState.LOADING)

        updateSpec.given(validModel)
            .`when`(AttemptLoginEvent)
            .then(
                assertThatNext(
                    hasModel(loadingModel),
                    hasEffects(ApiCallEffect(LoginRequest(validEmail, validPassword)) as LoginEffect)
                )
            )

    }

    @Test
    fun `attempted login was a success and token is saved` () {
        val validModel = LoginModel.EMPTY
            .inputEmail(validEmail)
            .inputPassword(validPassword)

        val successModel = LoginModel(validEmail,validPassword,ApiState.SUCCESS)

        val token = "login_token"

        updateSpec.given(validModel)
            .`when`(LoginSuccessEvent(LoginResponse(token)))
            .then(
                assertThatNext(
                    hasModel(successModel),
                    hasEffects(SaveTokenEffect(token) as LoginEffect)
                )
            )
    }

    @Test
    fun `attempted login failure due to auth error hence clears the input fields` () {
        val invalidModel = LoginModel.EMPTY
            .inputEmail(invalidEmail)
            .inputPassword(invalidPassword)


        updateSpec.given(invalidModel)
            .`when`(LoginAuthFailedEvent(NetworkError.AUTH_ERROR))
            .then(
                assertThatNext(
                    hasModel(LoginModel.EMPTY),
                    hasEffects(ClearFieldsEffect as LoginEffect)
                )
            )
    }

    @Test
    fun `attempted login failed due to network api failure` () {
        val validModel = LoginModel.EMPTY
            .inputEmail(validEmail)
            .inputPassword(validPassword)


        updateSpec.given(validModel)
            .`when`(LoginTimeoutFailedEvent(NetworkError.TIMEOUT))
            .then(
                assertThatNext(
                    hasModel(validModel.apiError()),
                    hasEffects(RetryEffect as LoginEffect)
                )
            )
    }

    @Test
    fun `attempted login success and token saved` () {
        val model = LoginModel(validEmail,validPassword,ApiState.SUCCESS)

        updateSpec.given(model)
            .`when`(TokenSavedEvent)
            .then(
                assertThatNext(
                    hasModel(model),
                    hasEffects(NavigateEffect(NavigateTo.HOME) as LoginEffect)
                )
            )
    }

    @Test
    fun `attempted login success and token not saved retry` () {
        val model = LoginModel(validEmail, validPassword, ApiState.SUCCESS)

        val token = "Login_token"

        updateSpec.given(model)
            .`when`(RetryEvent(token))
            .then(
                assertThatNext(
                    hasModel(model),
                    hasEffects(SaveTokenEffect(token) as LoginEffect)
                )
            )
    }
}