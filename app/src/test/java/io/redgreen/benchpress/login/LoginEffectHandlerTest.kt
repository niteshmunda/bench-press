package io.redgreen.benchpress.login

import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.whenever
import io.reactivex.Single
import io.redgreen.benchpress.login.db.Repository
import io.redgreen.benchpress.login.scheduler.AppSchedulers
import org.junit.Test

class LoginEffectHandlerTest {

    private val email = "testing.mobious@dunzo.in"
    private val password = "password123"

    private val actionMock: LoginAction = mock()
    private val apiMock: ApiService = mock()
    private val dbMock: Repository = mock()
    private val schedulerMock: AppSchedulers = mock()

    private val loginEffectHandlerTest = EffectHandlerTestCase(
        LoginEffectHandler.create(actionMock, apiMock, dbMock, schedulerMock)
    )

    @Test
    fun `verify login api is called when button is clicked`() {
        val request = LoginRequest(email, password)

        loginEffectHandlerTest.dispatchEffect(ApiCallEffect(request))
        verify(apiMock).login(request)
    }

    @Test
    fun `when login api is successful, then dispatch success event`() {
        val response = LoginResponse("token")

        whenever(apiMock.login(LoginRequest(email, password)))
            .thenReturn(Single.just(response))

        loginEffectHandlerTest.
                dispatchEffect(ApiCallEffect(LoginRequest(email, password)))

        loginEffectHandlerTest.assertOutgoingEvents(LoginSuccessEvent(LoginResponse("token")))
    }

    @Test
    fun `when login api fails, then dispatch failed event`() {
        // Simulate failed api response.
        whenever(apiMock.login(LoginRequest(email, password)))
            .thenReturn(Single.error {ClearFieldsEffect
                throw Error("Timeout")
            })

        loginEffectHandlerTest.
            dispatchEffect(ApiCallEffect(LoginRequest(email, password)))

        loginEffectHandlerTest.assertOutgoingEvents(LoginTimeoutFailedEvent(NetworkError.TIMEOUT))
    }

    @Test
    fun `when login response is saved, the send navigate to home event`() {

        val token = "token"

        whenever(dbMock.saveToken(token)).
            thenReturn(Single.just(true))

        loginEffectHandlerTest.
            dispatchEffect(SaveTokenEffect(token))

        loginEffectHandlerTest.assertOutgoingEvents(TokenSavedEvent)

        loginEffectHandlerTest.
            dispatchEffect(NavigateEffect(NavigateTo.HOME))

        verify(actionMock).navigateToHome()
    }

    @Test
    fun `when login response save fails, then user can retry`() {
        val token = "token"

        whenever(dbMock.saveToken(token)).
            thenReturn(Single.just(false))

        loginEffectHandlerTest.
            dispatchEffect(SaveTokenEffect(token))

        verify(dbMock).saveToken(token)

        loginEffectHandlerTest.assertOutgoingEvents(RetryEvent(token))
    }

    @Test
    fun `when api fails with auth error clear fields`() {

        loginEffectHandlerTest.
            dispatchEffect(ClearFieldsEffect)

        loginEffectHandlerTest.assertNoOutgoingEvents()

        verify(actionMock).clearFields()
    }
}