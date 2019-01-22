package io.redgreen.benchpress.login

import com.google.common.truth.Truth.assertThat
import org.junit.Test

class LoginModelTest {

    @Test
    fun `model contains email`() {

        val email = "abcd@xyz.com"

        val empty = LoginModel.EMPTY
        val loginModel = LoginModel(email, "")

        assertThat(empty.inputEmail(email))
            .isEqualTo(loginModel)
    }

    @Test
    fun `model contains password`() {

        val password = "danerys123"

        val empty = LoginModel.EMPTY
        val loginModel = LoginModel("", password)

        assertThat(empty.inputPassword(password))
            .isEqualTo(loginModel)
    }

    @Test
    fun `model contains email and password`() {

        val email = "abcd@xyz.com"
        val password = "danerys123"

        val empty = LoginModel.EMPTY
        val loginModel = LoginModel(email, password)

        val enteredModel = empty.inputEmail(email).inputPassword(password)
        assertThat(enteredModel)
            .isEqualTo(loginModel)
    }

    @Test
    fun `empty email is invalid`() {

        val email = ""
        val loginModel = LoginModel(email, "")

        assertThat(loginModel.isValidEmail)
            .isFalse()
    }

    @Test
    fun `empty password is invalid`() {

        val password = ""
        val loginModel = LoginModel("", password)

        assertThat(loginModel.isValidPassword)
            .isFalse()
    }

    @Test
    fun `email should contain @`() {

        val email = "test.email.123"
        val loginModel = LoginModel(email, "")

        assertThat(loginModel.isValidEmail)
            .isFalse()
    }

    @Test
    fun `password length cannot be less than 8`() {

        val password = "test123"
        val loginModel = LoginModel("", password)

        assertThat(loginModel.isValidPassword)
            .isFalse()
    }

    @Test
    fun `email should contain dot after @`() {

        val email = "test@test.com"
        val loginModel = LoginModel(email, "12345678")

        assertThat(loginModel.isValidEmail)
            .isTrue()
    }


}