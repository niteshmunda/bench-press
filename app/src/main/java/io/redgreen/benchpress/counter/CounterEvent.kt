package io.redgreen.benchpress.counter

sealed class CounterEvent

object IncrementEvent : CounterEvent()

object DecrementEvent : CounterEvent()
