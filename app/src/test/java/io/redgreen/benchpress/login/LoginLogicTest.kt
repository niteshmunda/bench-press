package io.redgreen.benchpress.login

import com.spotify.mobius.test.UpdateSpec

class LoginLogicTest{
    private val updateSpec = UpdateSpec<LoginModel, LoginEvent, LoginEffect> (LoginLogic::update)

    private val validEmail = "validEmail@test.com"
    private val validPassword = "validPassword"
}