package io.redgreen.benchpress.login

import com.google.common.truth.Truth.assertThat
import com.spotify.mobius.test.NextMatchers.hasModel
import com.spotify.mobius.test.NextMatchers.hasNoEffects
import com.spotify.mobius.test.UpdateSpec
import com.spotify.mobius.test.UpdateSpec.assertThatNext
import org.junit.Test

class LoginLogicTest {

    private val updateSpec = UpdateSpec<LoginModel, LoginEvent, Nothing>(LoginLogic::update)

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
    fun `when user enters invalid email and password, then user cannot login`() {
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
    fun `when user enters valid email and password, then user can login`() {
        val emptyModel = LoginModel.EMPTY
        val validMail = "name@companyDomain.com"
        val validPassword = "secretPassword"

        val validModel = emptyModel
            .inputEmail(validMail)
            .inputPassword(validPassword)
        updateSpec.given(emptyModel)
            .`when`(InputEmailEvent(validMail), InputPasswordEvent(validPassword))
            .then(
                assertThatNext(
                    hasModel(validModel),
                    hasNoEffects()
                )
            )
        assertThat(validModel.isReadyForLogin)
            .isTrue()
    }
}
