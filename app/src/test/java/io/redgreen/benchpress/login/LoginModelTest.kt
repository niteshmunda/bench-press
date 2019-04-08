package io.redgreen.benchpress.login

import com.google.common.truth.Truth.assertThat
import org.junit.Test

class LoginModelTest {
  @Test
  fun `when model has valid email and password, then it is ready for login`() {
    val validLoginModel = LoginModel
      .BLANK
      .emailChanged("shailesh@dunzo.in")
      .passwordChanged("some-secret")

    assertThat(validLoginModel.isReadyForLogin)
      .isTrue()
  }

  @Test
  fun `when model has an invalid email or password, then it is not ready for login`() {
    val invalidLoginModel = LoginModel
      .BLANK
      .emailChanged("some")
      .passwordChanged("123")

    assertThat(invalidLoginModel.isReadyForLogin)
      .isFalse()
  }
}
