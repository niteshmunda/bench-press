package io.redgreen.benchpress.login.domain

import com.google.common.truth.Truth.assertThat
import org.junit.Test

class PasswordTest {
  @Test
  fun `empty password is invalid`() {
    assertThat(Password("").isValid())
      .isFalse()
  }

  @Test
  fun `short passwords are invalid`() {
    assertThat(Password("short").isValid())
      .isFalse()
  }

  @Test
  fun `passwords with 8 chars are valid`() {
    assertThat(Password("12345678").isValid())
      .isTrue()
  }

  @Test
  fun `passwords greater than 8 chars are valid`() {
    assertThat(Password("a-very-long-password").isValid())
      .isTrue()
  }
}
