package io.redgreen.benchpress.hellostranger

sealed class HelloStrangerEvent

data class EnterNameEvent(val name : String) : HelloStrangerEvent()

object DeleteNameEvent : HelloStrangerEvent()