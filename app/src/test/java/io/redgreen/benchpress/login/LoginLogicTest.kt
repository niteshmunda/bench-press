package io.redgreen.benchpress.login

import com.spotify.mobius.test.NextMatchers.hasModel
import com.spotify.mobius.test.NextMatchers.hasNoEffects
import com.spotify.mobius.test.UpdateSpec
import com.spotify.mobius.test.UpdateSpec.assertThatNext
import org.junit.Test

class LoginLogicTest {
  @Test
  fun `when user changes email, then update email`() {
    val updateSpec = UpdateSpec<LoginModel, LoginEvent, LoginEffect>(LoginLogic::update)
    val blankModel = LoginModel.BLANK
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
}
