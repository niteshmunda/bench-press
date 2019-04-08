package io.redgreen.benchpress.login

import com.spotify.mobius.test.NextMatchers.*
import com.spotify.mobius.test.UpdateSpec
import com.spotify.mobius.test.UpdateSpec.assertThatNext
import org.junit.Test

class LoginLogicTest {
  private val updateSpec = UpdateSpec<LoginModel, LoginEvent, LoginEffect>(LoginLogic::update)
  private val blankModel = LoginModel.BLANK
  private val email = "nainesh@dunzo.in"
  private val password = "some-secret-password"

  @Test
  fun `when user changes email, then update email`() {
    updateSpec
      .given(blankModel)
      .`when`(EmailChangedEvent(email))
      .then(
        assertThatNext(
          hasModel(blankModel.emailChanged(email)),
          hasNoEffects()
        )
      )
  }

  @Test
  fun `when user changes password, then update password`() {
    updateSpec
      .given(blankModel)
      .`when`(PasswordChangedEvent(password))
      .then(
        assertThatNext(
          hasModel(blankModel.passwordChanged(password)),
          hasNoEffects()
        )
      )
  }

  @Test
  fun `when model is ready for login, then user can make a network call`() {
    val readyForLoginModel = blankModel
      .emailChanged(email)
      .passwordChanged(password)

    updateSpec
      .given(readyForLoginModel)
      .`when`(AttemptLoginEvent)
      .then(
        assertThatNext(
          hasModel(readyForLoginModel.attemptLogin()),
          hasEffects(AttemptLoginEffect as LoginEffect)
        )
      )
  }

  @Test
  fun `when login is successful, then user can go to home screen`() {
    val loggingInLoginModel = blankModel
      .emailChanged(email)
      .passwordChanged(password)
      .attemptLogin()
    val token = "some-auth-token"

    updateSpec
      .given(loggingInLoginModel)
      .`when`(UserAuthenticatedEvent(token))
      .then(
        assertThatNext(
          hasModel(loggingInLoginModel.userAuthenticated()),
          hasEffects(GoToHomeEffect(token) as LoginEffect)
        )
      )
  }
}
