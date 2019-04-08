package io.redgreen.benchpress.login

import com.spotify.mobius.test.NextMatchers.hasModel
import com.spotify.mobius.test.NextMatchers.hasNoEffects
import com.spotify.mobius.test.UpdateSpec
import com.spotify.mobius.test.UpdateSpec.assertThatNext
import org.junit.Test

class LoginLogicTest {
  private val updateSpec = UpdateSpec<LoginModel, LoginEvent, LoginEffect>(LoginLogic::update)
  private val blankModel = LoginModel.BLANK

  @Test
  fun `when user changes email, then update email`() {
    val email = "nainesh@dunzo.in"

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
    val password = "some-secret-password"

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
}
